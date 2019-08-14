package com.nts.reservation.service;

import java.util.ArrayList;
import java.util.Map;

import com.nts.reservation.dto.Reservation;
import com.nts.reservation.dto.ReservationParam;

public interface ReservationService {
	public Map<String, Object> getReserveInfo(int displayInfoId);

	public void saveReserveInfo(ReservationParam reservationParam);

	public Map<String, ArrayList<Reservation>> getMyReservations(String reservationEmail);
}
