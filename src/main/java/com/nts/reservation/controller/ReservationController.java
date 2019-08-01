package com.nts.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {

	@GetMapping("detail")
	public String productDetail(@RequestParam(value = "id") int displayInfoId) {
		return "detail";
	}
}
