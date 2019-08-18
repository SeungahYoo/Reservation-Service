package com.nts.reservation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nts.reservation.dto.Reservation;
import com.nts.reservation.dto.ReservationParam;
import com.nts.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api")
public class ReservationApiController {
	ReservationService reservationService;

	public ReservationApiController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@PostMapping("reserve")
	public ModelAndView saveReserveInfo(@ModelAttribute("reservationParam") ReservationParam reservationParam) {
		System.out.println(reservationParam);
		
		reservationService.saveReserveInfo(reservationParam);
		return new ModelAndView("redirect:/user-check");
	}

	@GetMapping("reserve")
	public Map<String, Object> getReserveInfo(@RequestParam(name = "id") int displayInfoId) {
		return reservationService.getReserveInfo(displayInfoId);
	}

	@GetMapping("my-reservation")
	public Map<String, List<Reservation>> getMyReservations(
		@RequestParam(name = "email") String reservationEmail) {
		return reservationService.getMyReservations(reservationEmail);
	}

	@GetMapping("cancel")
	public void cancelReservation(@RequestParam(name = "id") int reservationInfoId) {
		reservationService.cancelReservation(reservationInfoId);
	}

}
