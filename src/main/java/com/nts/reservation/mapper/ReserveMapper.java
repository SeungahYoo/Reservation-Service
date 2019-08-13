package com.nts.reservation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Price;
import com.nts.reservation.dto.ReservationParam;

@Repository
public interface ReserveMapper {
	void insertReserveInfo(ReservationParam reservationParam);

	void insertReservationInfoPrices(@Param("reservationPrices") List<Price> reservationPrices);
}
