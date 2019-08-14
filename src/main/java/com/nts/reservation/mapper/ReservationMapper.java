package com.nts.reservation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Reservation;
import com.nts.reservation.dto.ReservationParam;

@Repository
public interface ReservationMapper {
	void insertReserveInfo(ReservationParam reservationParam);

	void insertReserveInfoPrices(@Param("reservationParam") ReservationParam reservationParam);

	List<Reservation> selectReservations(String reservationEmail);

	void updateReservationCanceled(int reservationInfoId);
}
