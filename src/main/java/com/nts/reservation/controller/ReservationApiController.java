package com.nts.reservation.controller;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.Reservation;
import com.nts.reservation.dto.ReservationParam;
import com.nts.reservation.service.CommentService;
import com.nts.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api")
public class ReservationApiController {
	private static final Pattern NAME_PATTERN = Pattern.compile("[^\\s]+");
	private static final Pattern TELEPHONE_PATTERN = Pattern.compile("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile(
		"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

	private final ReservationService reservationService;
	private final CommentService commentService;

	public ReservationApiController(ReservationService reservationService, CommentService commentService) {
		this.reservationService = reservationService;
		this.commentService = commentService;
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
		if (StringUtils.isEmpty(reservationParam.getReservationEmail())
			|| StringUtils.isEmpty(reservationParam.getReservationName())
			|| StringUtils.isEmpty(reservationParam.getReservationTelephone())
			|| !EMAIL_PATTERN.matcher(reservationParam.getReservationEmail()).matches()
			|| !NAME_PATTERN.matcher(reservationParam.getReservationName()).matches()
			|| !TELEPHONE_PATTERN.matcher(reservationParam.getReservationTelephone()).matches()) {

			throw new IllegalArgumentException("Invalid ReservationParam");
		}

		reservationService.saveReserveInfo(reservationParam);

		return new ModelAndView("redirect:/user-check");
	}

	@PostMapping("comments")
	public ModelAndView saveComment(Comment comment, @RequestParam("files") List<MultipartFile> commentImages) {
		commentService.saveComment(comment, commentImages);
		return new ModelAndView("redirect:/user-check");
	}

}
