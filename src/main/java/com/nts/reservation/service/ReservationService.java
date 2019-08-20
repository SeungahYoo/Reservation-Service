package com.nts.reservation.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.Reservation;
import com.nts.reservation.dto.ReservationParam;

public interface ReservationService {
	Map<String, Object> getReserveInfo(int displayInfoId);

	void saveReserveInfo(ReservationParam reservationParam);

	Map<String, List<Reservation>> getMyReservations(String reservationEmail);

	int cancelReservation(String cookieEmail, int reservationInfoId);

	void saveComment(Comment comment, List<MultipartFile> commentImages);
}
