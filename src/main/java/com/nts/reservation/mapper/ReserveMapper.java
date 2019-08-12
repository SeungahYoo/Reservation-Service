package com.nts.reservation.mapper;

import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Price;
import com.nts.reservation.dto.ReservationParam;

@Repository
public interface ReserveMapper {
	void insertReserveInfo(ReservationParam reservationParam);

	void insertReservationInfoPrice(Price price);
}
