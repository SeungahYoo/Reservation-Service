package com.nts.reservation.service;

import java.util.Map;

import com.nts.reservation.dto.ReservationParam;

public interface ReserveService {
	public Map<String, Object> getReserveInfo(int displayInfoId);

	public void saveReserveInfo(ReservationParam reservationParam);
}
