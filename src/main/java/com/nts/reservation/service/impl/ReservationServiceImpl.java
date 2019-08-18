package com.nts.reservation.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.reservation.dto.DisplayInfo;
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
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
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
		displayMap.put("reservationDate", LocalDate.now()
											.plusDays(ThreadLocalRandom.current().nextInt(6))
											.format(formatter));

		return displayMap;
	}

	@Override
	@Transactional
	public void saveReserveInfo(ReservationParam reservationParam) {
		reservationMapper.insertReserveInfo(reservationParam);
		reservationMapper.insertReserveInfoPrices(reservationParam);
	}

	@Override
	public Map<String, List<Reservation>> getMyReservations(String reservationEmail) {
		List<Reservation> myReservations = reservationMapper.selectReservations(reservationEmail);

		List<Reservation> canceledReservations = new ArrayList<>();
		List<Reservation> confirmedReservations = new ArrayList<>();
		List<Reservation> usedReservations = new ArrayList<>();

		for (Reservation reservation : myReservations) {
			if (reservation.isCancelYn()) {
				canceledReservations.add(reservation);
			} else {
				if (reservation.getReservationDate().isBefore(LocalDateTime.now())) {
					usedReservations.add(reservation);
				} else {
					confirmedReservations.add(reservation);
				}
			}
		}

		Map<String, List<Reservation>> reservationsMap = new HashMap<>();

		reservationsMap.put("canceledReservations", canceledReservations);
		reservationsMap.put("confirmedReservations", confirmedReservations);
		reservationsMap.put("usedReservations", usedReservations);

		return reservationsMap;
	}

	@Override
	public void cancelReservation(int reservationInfoId) {
		reservationMapper.updateReservationCanceled(reservationInfoId);
	}

}
