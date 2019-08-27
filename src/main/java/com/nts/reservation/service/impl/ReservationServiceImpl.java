package com.nts.reservation.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final Pattern NAME_PATTERN = Pattern.compile("[^\\s]+");
	private static final Pattern TELEPHONE_PATTERN = Pattern.compile("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile(
		"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

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
		displayMap.put("reservationDate", LocalDate.now()
			.plusDays(ThreadLocalRandom.current().nextInt(1, 6))
			.format(FORMATTER));

		return displayMap;
	}

	@Override
	@Transactional
	public void saveReserveInfo(ReservationParam reservationParam) {
		if (!isValidReservationParam(reservationParam)) {
			return;
		}

		reservationMapper.insertReserveInfo(reservationParam);
		reservationMapper.insertReserveInfoPrices(reservationParam);
	}

	private boolean isValidReservationParam(ReservationParam reservationParam) {
		String errorTarget = "";

		if (StringUtils.isEmpty(reservationParam.getReservationEmail())) {
			errorTarget += "reservation email, ";
		} else if (!EMAIL_PATTERN.matcher(reservationParam.getReservationEmail()).matches()) {
			errorTarget += "reservation email pattern, ";
		}

		if (StringUtils.isEmpty(reservationParam.getReservationName())) {
			errorTarget += "reservation name, ";
		} else if (!NAME_PATTERN.matcher(reservationParam.getReservationName()).matches()) {
			errorTarget += "reservation name pattern, ";
		}

		if (StringUtils.isEmpty(reservationParam.getReservationTelephone())) {
			errorTarget += "reservation telephone, ";
		} else if (!TELEPHONE_PATTERN.matcher(reservationParam.getReservationTelephone()).matches()) {
			errorTarget += "reservation telephone pattern";
		}

		if (errorTarget != "") {
			throw new IllegalArgumentException(
				"Invalid ReservationParam : " + reservationParam + "/ invalid value(" + errorTarget + ")");
		}

		return true;

	}

	@Override
	public Map<String, List<Reservation>> getMyReservations(String reservationEmail) {
		List<Reservation> myReservations = reservationMapper.selectReservations(reservationEmail);

		List<Reservation> canceledReservations = new ArrayList<>();
		List<Reservation> confirmedReservations = new ArrayList<>();
		List<Reservation> usedReservations = new ArrayList<>();
		LocalDateTime nowDate = LocalDateTime.now();

		for (Reservation reservation : myReservations) {
			if (reservation.isCancelYn()) {
				canceledReservations.add(reservation);
			} else {
				if (reservation.getReservationDate().isBefore(nowDate)) {
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
	public int cancelReservation(String cookieEmail, int reservationInfoId) {
		return reservationMapper.updateReservationCanceled(cookieEmail, reservationInfoId);
	}

}
