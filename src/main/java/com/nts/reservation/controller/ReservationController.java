package com.nts.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {

	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String productDetail(@RequestParam(value = "id") int productId) {
		System.out.println("here I am");
		return "detail";
	}
}
