package com.nts.reservation.dto;

import java.time.LocalDateTime;

public class DisplayInfo {
	private int categoryId;
	private String categoryName;
	private LocalDateTime createDate;
	private int displayInfoId;
	private String email;
	private String homepage;
	private LocalDateTime modifyDate;
	private String opiningHours;
	private String placeLot;
	private String placeName;
	private String placeStreet;
	private String productContent;
	private String productDescription;
	private String productEvent;
	private String telephone;
	private int productId;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public int getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getOpiningHours() {
		return opiningHours;
	}

	public void setOpiningHours(String opiningHours) {
		this.opiningHours = opiningHours;
	}

	public String getPlaceLot() {
		return placeLot;
	}

	public void setPlaceLot(String placeLot) {
		this.placeLot = placeLot;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceStreet() {
		return placeStreet;
	}

	public void setPlaceStreet(String placeStreet) {
		this.placeStreet = placeStreet;
	}

	public String getProductContent() {
		return productContent;
	}

	public void setProductContent(String productContent) {
		this.productContent = productContent;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductEvent() {
		return productEvent;
	}

	public void setProductEvent(String productEvent) {
		this.productEvent = productEvent;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "DisplayInfo [categoryId=" + categoryId + ", categoryName=" + categoryName + ", createDate=" + createDate
			+ ", displayInfoId=" + displayInfoId + ", email=" + email + ", homepage=" + homepage + ", modifyDate="
			+ modifyDate + ", opiningHours=" + opiningHours + ", placeLot=" + placeLot + ", placeName=" + placeName
			+ ", placeStreet=" + placeStreet + ", productContent=" + productContent + ", productDescription="
			+ productDescription + ", productEvent=" + productEvent + ", telephone=" + telephone + ", productId="
			+ productId + "]";
	}

}
