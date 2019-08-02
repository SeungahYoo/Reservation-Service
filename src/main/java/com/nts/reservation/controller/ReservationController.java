package com.nts.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Controller
public class ReservationController {

	@GetMapping("detail")
	public String productDetail(@RequestParam(value = "id") int displayInfoId) {
		return "detail";
	}

	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public String handleMethodArgumentTypeMismatch(
		MethodArgumentTypeMismatchException ex, WebRequest request) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		System.out.println("[Invalid Value] " + error);
		return "mainpage";
	}
}
