package com.nts.reservation.dto;

public class Promotion {
	private int id;
	private int productId;
	private String promotionImageUrl;
	private String promotionImageFileId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getPromotionImageUrl() {
		return promotionImageUrl;
	}
	public void setPromotionImageUrl(String promotionImageUrl) {
		this.promotionImageUrl = promotionImageUrl;
	}
	public String getPromotionImageFileId() {
		return promotionImageFileId;
	}
	public void setPromotionImageFileId(String promotionImageFileId) {
		this.promotionImageFileId = promotionImageFileId;
	}
	
	@Override
	public String toString() {
		return "Promotion [id=" + id + ", productId=" + productId + ", promotionImageUrl=" + promotionImageUrl
				+ ", promotionImageFileId=" + promotionImageFileId + "]";
	}

}
