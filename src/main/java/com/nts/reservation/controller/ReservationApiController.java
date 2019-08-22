package com.nts.reservation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nts.reservation.dto.Reservation;
import com.nts.reservation.dto.ReservationParam;
import com.nts.reservation.service.CommentService;
import com.nts.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api")
public class ReservationApiController {

	private final ReservationService reservationService;

	public ReservationApiController(ReservationService reservationService, CommentService commentService) {
		this.reservationService = reservationService;
	}

	@GetMapping("reserve")
	public Map<String, Object> getReserveInfo(@RequestParam(name = "id") int displayInfoId) {
		return reservationService.getReserveInfo(displayInfoId);
	}

	@GetMapping("my-reservation")
	public Map<String, List<Reservation>> getMyReservations(@RequestParam(name = "email") String reservationEmail) {
		return reservationService.getMyReservations(reservationEmail);
	}

	@PutMapping("cancel")
	public void cancelReservation(@CookieValue(value = "email") String cookieEmail,
		@RequestParam(name = "id") int reservationInfoId) {
		int successCount = reservationService.cancelReservation(cookieEmail, reservationInfoId);

		if (successCount <= 0) {
			throw new DataRetrievalFailureException("Cannot cancel a reservation : reservation id mismatch with email");
		}
	}

	@PostMapping("reserve")
	public ModelAndView saveReserveInfo(@ModelAttribute("reservationParam") ReservationParam reservationParam) {
		reservationService.saveReserveInfo(reservationParam);

		return new ModelAndView("redirect:/user-check");
	}

}
