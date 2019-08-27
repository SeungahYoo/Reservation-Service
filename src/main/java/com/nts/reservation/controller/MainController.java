package com.nts.reservation.controller;

import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	private static final Pattern EMAIL_PATTERN = Pattern.compile(
		"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

	@GetMapping("mainpage")
	public String mainpage() {
		return "mainpage";
	}

	@GetMapping("detail")
	public String productDetail(@RequestParam(value = "id") int displayInfoId) {
		if (displayInfoId <= 0) {
			throw new IllegalArgumentException("DisplayInfoId is below 0.");
		}
		return "detail";
	}

	@GetMapping("review")
	public String review(@RequestParam(value = "id") int displayInfoId) {
		return "review";
	}

	@GetMapping("reserve")
	public String reserve(@RequestParam(value = "id") int displayInfoId,
		@CookieValue(value = "email", required = false) String email) {
		return "reserve";
	}

	@GetMapping("my-reservation")
	public String myReservation() {
		return "myreservation";
	}

	@GetMapping("user-check")
	public String userCheck(@CookieValue(value = "email", required = false) String email) {
		if (email == null) {
			return "redirect:booking-login";
		} else {
			return "redirect:my-reservation";
		}
	}

	@GetMapping("booking-login")
	public String bookingLogin() {
		return "bookinglogin";
	}

	@PostMapping("booking-login")
	public String loginProcess(@RequestParam("resrv_email") String email, HttpServletResponse response) {
		if (StringUtils.isEmpty(email) || !EMAIL_PATTERN.matcher(email).matches()) {
			throw new IllegalArgumentException("Invalid ReservationEmail");
		}

		Cookie cookie = new Cookie("email", email);
		cookie.setMaxAge(60 * 20);
		cookie.setPath("/");
		response.addCookie(cookie);

		return "redirect:user-check";
	}

	@GetMapping("logout")
	public String logout(HttpServletResponse response) {
		Cookie cookie = new Cookie("email", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");

		response.addCookie(cookie);

		return "redirect:mainpage";
	}

	@GetMapping("review-write")
	public String reviewWrite(@RequestParam(value = "reservationInfoId") int reservationInfoId,
		@RequestParam("displayInfoId") int displayInfoId,
		@RequestParam("title") String productDescription) {

		return "reviewWrite";
	}

	@PostMapping("comments")
	public String saveComment(@RequestParam("reservationInfoId") int reservationInfoId) {

		return "reviewWrite";
	}

}
