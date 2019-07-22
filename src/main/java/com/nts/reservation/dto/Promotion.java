package com.nts.reservation.dto;

public class Promotion{
	private String promotionImage;

	public String getpromotionImage() {
		return promotionImage;
	}

	public void setpromotionImage(String promotionImage) {
		this.promotionImage = promotionImage;
	}

	@Override
	public String toString() {
		return "Promotion [promotionImage=" + promotionImage + "]";
	}
	
}
