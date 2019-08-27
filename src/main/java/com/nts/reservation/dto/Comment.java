package com.nts.reservation.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {
	private String comment;
	private int commentId;
	private List<CommentImage> commentImages;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	private int productId;
	private LocalDateTime reservationDate;
	private String reservationEmail;
	private int reservationInfoId;
	private int displayInfoId;
	private String reservationName;
	private String reservationTelephone;
	private int score;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public List<CommentImage> getCommentImages() {
		return commentImages;
	}

	public void setCommentImages(List<CommentImage> commentImages) {
		this.commentImages = commentImages;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	@JsonProperty("createDate")
	public String getCreateDateView() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d.");
		return ObjectUtils.defaultIfNull(createDate.format(formatter), null);
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public LocalDateTime getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDateTime reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getReservationEmail() {
		return reservationEmail;
	}

	@JsonProperty("email")
	public String getReservationEmailView() {

		return StringUtils.substring(reservationEmail, 0, 4) + "****";
	}

	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}

	public int getReservationInfoId() {
		return reservationInfoId;
	}

	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}

	public int getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
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

	public int getScore() {
		return score;
	}

	@JsonProperty("score")
	public String getScoreView() {
		return score + ".0";
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Comment [comment=" + comment + ", commentId=" + commentId + ", commentImages=" + commentImages
			+ ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", productId=" + productId
			+ ", reservationDate=" + reservationDate + ", reservationEmail=" + reservationEmail + ", reservationInfoId="
			+ reservationInfoId + ", displayInfoId=" + displayInfoId + ", reservationName=" + reservationName
			+ ", reservationTelephone=" + reservationTelephone + ", score=" + score + "]";
	}

}
