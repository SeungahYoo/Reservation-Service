package com.nts.reservation.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nts.reservation.dto.ReservationParam;
import com.nts.reservation.service.ReserveService;

@RestController
@RequestMapping(path = "/api")
public class ReserveApiController {
	ReserveService reserveService;

	public ReserveApiController(ReserveService reserveService) {
		this.reserveService = reserveService;
	}

	@PostMapping("reserve")
	public void saveReserveInfo(@ModelAttribute("reservationParam") ReservationParam reservationParam) {
		reserveService.saveReserveInfo(reservationParam);
	}

	@GetMapping("reserve")
	public Map<String, Object> getReserveInfo(@RequestParam(name = "id") int displayInfoId) {
		return reserveService.getReserveInfo(displayInfoId);
	}

}
