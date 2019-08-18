package com.nts.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservationParam {
	private int id;
	private int displayInfoId;
	private List<Price> prices;
	private int productId;
	private String reservationEmail;
	private String reservationName;
	private String reservationTelephone;
	private LocalDateTime reservationDate;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public int getDisplayInfoId() {
		return displayInfoId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getReservationEmail() {
		return reservationEmail;
	}

	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	public String getReservationTelephone() {
		return reservationTelephone;
	}

	public void setReservationTelephone(String reservationTelephone) {
		this.reservationTelephone = reservationTelephone;
	}

	public LocalDateTime getReservationDate() {
		return reservationDate;
	}
	
	public void setReservationDate(String reservationDate) {
		this.reservationDate = LocalDate.parse(reservationDate, formatter).atStartOfDay();
	}

	@Override
	public String toString() {
		return "ReservationParam [id=" + id + ", displayInfoId=" + displayInfoId + ", prices=" + prices + ", productId="
			+ productId + ", reservationEmail=" + reservationEmail + ", reservationName=" + reservationName
			+ ", reservationTelephone=" + reservationTelephone + ", reservationDate=" + reservationDate + "]";
	}

}
