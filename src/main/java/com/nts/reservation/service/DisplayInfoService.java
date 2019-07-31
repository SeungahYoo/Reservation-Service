package com.nts.reservation.service;

import java.util.List;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;
import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.DisplayInfoImage;
import com.nts.reservation.dto.ProductImage;
import com.nts.reservation.dto.ProductPrice;

public interface DisplayInfoService {
	DisplayInfo getDisplayInfo(int displayInfoId);

	DisplayInfoImage getDisplayInfoImage(int displayInfoId);

	List<ProductImage> getProductImages(int productId);

	List<ProductPrice> getProductPrices(int productId);

	List<Comment> getComments(int productId);

	List<CommentImage> getCommentImages(int commentId);
}
