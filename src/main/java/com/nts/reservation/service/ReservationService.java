package com.nts.reservation.service;

import java.util.List;
import java.util.Map;

import com.nts.reservation.dto.Reservation;
import com.nts.reservation.dto.ReservationParam;

public interface ReservationService {
	Map<String, Object> getReserveInfo(int displayInfoId);

	void saveReserveInfo(ReservationParam reservationParam);

	Map<String, List<Reservation>> getMyReservations(String reservationEmail);

	void cancelReservation(int reservationInfoId);
}
