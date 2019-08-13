package com.nts.reservation.service;

import java.util.List;
import java.util.Map;

import com.nts.reservation.dto.Reservation;
import com.nts.reservation.dto.ReservationParam;

public interface ReservationService {
	public Map<String, Object> getReserveInfo(int displayInfoId);

	public void saveReserveInfo(ReservationParam reservationParam);

	public List<Reservation> getMyReservations(String reservationEmail);
}
