package com.nts.reservation.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.Price;
import com.nts.reservation.dto.Reservation;
import com.nts.reservation.dto.ReservationParam;
import com.nts.reservation.mapper.DisplayInfoMapper;
import com.nts.reservation.mapper.ProductMapper;
import com.nts.reservation.mapper.ReservationMapper;
import com.nts.reservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
	private final DisplayInfoMapper displayInfoMapper;
	private final ProductMapper productMapper;
	private final ReservationMapper reservationMapper;

	public ReservationServiceImpl(DisplayInfoMapper displayInfoMapper, ProductMapper productMapper,
		ReservationMapper reservationMapper) {
		this.displayInfoMapper = displayInfoMapper;
		this.productMapper = productMapper;
		this.reservationMapper = reservationMapper;
	}

	@Override
	public Map<String, Object> getReserveInfo(int displayInfoId) {
		DisplayInfo displayInfo = displayInfoMapper.selectDisplayInfo(displayInfoId);
		if (displayInfo == null) {
			throw new DataRetrievalFailureException(
				"The expedted data could not be retrieved. displayInfoId: " + displayInfoId);
		}
		int productId = displayInfo.getProductId();

		Map<String, Object> displayMap = new HashMap<>();
		displayMap.put("displayInfo", displayInfo);
		displayMap.put("productImages", productMapper.selectProductImages(productId));
		displayMap.put("productPrices", productMapper.selectProductPrices(productId));

		return displayMap;
	}

	@Override
	@Transactional
	public void saveReserveInfo(ReservationParam reservationParam) {
		LocalDate nowDate = LocalDate.now();
		long randomEpochDay = ThreadLocalRandom.current()
			.longs(nowDate.toEpochDay(), nowDate.plusDays(5).toEpochDay())
			.findAny().getAsLong();

		reservationParam.setReservationDate(LocalDate.ofEpochDay(randomEpochDay));

		reservationMapper.insertReserveInfo(reservationParam);
		int reservationInfoId = reservationParam.getId();
		List<Price> reservationPrices = reservationParam.getPrices();
		for (Price price : reservationParam.getPrices()) {
			price.setReservationInfoId(reservationInfoId);
		}
		System.out.println(reservationPrices);

		reservationMapper.insertReserveInfoPrices(reservationPrices);

	}

	@Override
	public Map<String, ArrayList<Reservation>> getMyReservations(String reservationEmail) {
		List<Reservation> myReservations = reservationMapper.selectReservations(reservationEmail);

		ArrayList<Reservation> canceledReservations = new ArrayList<>();
		ArrayList<Reservation> confirmedReservations = new ArrayList<>();
		ArrayList<Reservation> usedReservations = new ArrayList<>();

		for (Reservation reservation : myReservations) {
			if (reservation.isCancelYn()) {
				canceledReservations.add(reservation);
				continue;
			}
			if (reservation.getReservationDate().isBefore(LocalDateTime.now())) {
				usedReservations.add(reservation);
				continue;
			}
			confirmedReservations.add(reservation);
		}

		Map<String, ArrayList<Reservation>> reservationsMap = new HashMap<>();

		reservationsMap.put("canceledReservations", canceledReservations);
		reservationsMap.put("confirmedReservations", confirmedReservations);
		reservationsMap.put("usedReservations", usedReservations);

		return reservationsMap;
	}

}
