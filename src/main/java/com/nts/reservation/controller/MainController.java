package com.nts.reservation.controller;

import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nts.reservation.dto.ReservationParam;
import com.nts.reservation.service.ReservationService;

@Controller
public class MainController {
	private final ReservationService reservationService;
	private static final Pattern EMAIL_PATTERN = Pattern
		.compile("/^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$/i");
	private static final Pattern NAME_PATTERN = Pattern.compile("[^\\s]+");
	private static final Pattern TELEPHONE_PATTERN = Pattern.compile("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$");

	public MainController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

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

	@PostMapping("reserve")
	public ModelAndView saveReserveInfo(@ModelAttribute("reservationParam") ReservationParam reservationParam) {
		if (StringUtils.isEmpty(reservationParam.getReservationEmail())
			|| StringUtils.isEmpty(reservationParam.getReservationName())
			|| StringUtils.isEmpty(reservationParam.getReservationTelephone())
			|| EMAIL_PATTERN.matcher(reservationParam.getReservationEmail()).matches()
			|| NAME_PATTERN.matcher(reservationParam.getReservationName()).matches()
			|| TELEPHONE_PATTERN.matcher(reservationParam.getReservationTelephone()).matches()) {
			throw new IllegalArgumentException("Invalid ReservationParam");
		}

		reservationService.saveReserveInfo(reservationParam);

		return new ModelAndView("redirect:/user-check");
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
		if (email.length() <= 0 || EMAIL_PATTERN.matcher(email).matches()) {
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
