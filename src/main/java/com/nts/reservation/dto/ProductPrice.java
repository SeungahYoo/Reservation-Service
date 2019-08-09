package com.nts.reservation.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductPrice {
	private LocalDateTime createDate;
	private int discountRate;
	private LocalDateTime modifyDate;
	private int price;
	private String priceTypeName;
	private int productId;
	private int productPriceId;

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public int getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPriceTypeName() {
		return priceTypeName;
	}

	@JsonProperty("priceTypeName")
	public String getPriceTypeNameView() {
		switch (priceTypeName) {
			case "A":
				return "성인";
			case "Y":
				return "청소년";
			case "B":
				return "유아";
			case "S":
				return "셋트";
			case "D":
				return "장애인";
			case "C":
				return "지역주민";
			case "E":
				return "얼리버드";
			case "V":
				return "VIP";
			case "R":
				return "R석";
			default:
				return "";
		}

	}

	public void setPriceTypeName(String priceTypeName) {
		this.priceTypeName = priceTypeName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductPriceId() {
		return productPriceId;
	}

	public void setProductPriceId(int productPriceId) {
		this.productPriceId = productPriceId;
	}

	@Override
	public String toString() {
		return "ProductPrices [createDate=" + createDate + ", discountRate=" + discountRate + ", modifyDate="
			+ modifyDate + ", price=" + price + ", priceTypeName=" + priceTypeName + ", productId=" + productId
			+ ", productPriceId=" + productPriceId + "]";
	}

}
