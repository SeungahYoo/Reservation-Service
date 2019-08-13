package com.nts.reservation.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
//		long randomEpochDay = ThreadLocalRandom.current().longs(nowDate.toEpochDay(),nowDate.plusDays(5).toEpochDay()).findAny().getAsLong();
//		
//		reservationParam.setReservationDate(LocalDate.ofEpochDay(randomEpochDay));

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
	public List<Reservation> getMyReservations(String reservationEmail) {
		System.out.println("ReservationServiceImpl email: " + reservationEmail);
		System.out.println(reservationMapper.selectReservations(reservationEmail));
		return reservationMapper.selectReservations(reservationEmail);
	}

}
