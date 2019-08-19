package com.nts.reservation.controller;

import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
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
		if (email.length() <= 0 ||
			Pattern.compile("/^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$/i")
				.matcher(email).matches()) {
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

}
