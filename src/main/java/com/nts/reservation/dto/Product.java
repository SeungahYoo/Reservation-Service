package com.nts.reservation.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
	private int displayInfoId;
	private String placeName;
	private String productContent;
	private String productDescription;
	private int productId;
	private String productImageUrl;
	private List<ProductPrice> productPrices;
	private Double productScoreAverage;
	private int commentsCount;

	public int getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public List<ProductPrice> getProductPrices() {
		return productPrices;
	}

	public void setProductPrices(List<ProductPrice> productPrices) {
		this.productPrices = productPrices;
	}

	public Double getProductScoreAverage() {
		return productScoreAverage;
	}

	@JsonProperty("productScoreAverage")
	public String getProductScoreAverageView() {
		if (productScoreAverage != null) {
			return String.format("%.2f", productScoreAverage);
		}
		return null;
	}

	public void setProductScoreAverage(Double productScoreAverage) {
		this.productScoreAverage = productScoreAverage;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	@Override
	public String toString() {
		return "Product [displayInfoId=" + displayInfoId + ", placeName=" + placeName + ", productContent="
			+ productContent + ", productDescription=" + productDescription + ", productId=" + productId
			+ ", productImageUrl=" + productImageUrl + ", productPrices=" + productPrices + "]";
	}

}
