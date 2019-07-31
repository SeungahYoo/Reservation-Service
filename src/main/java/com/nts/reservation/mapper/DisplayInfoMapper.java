package com.nts.reservation.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;
import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.DisplayInfoImage;
import com.nts.reservation.dto.ProductImage;
import com.nts.reservation.dto.ProductPrice;

@Repository
public interface DisplayInfoMapper {
	public DisplayInfo selectDisplayInfo(int displayInfoId);

	public DisplayInfoImage selectDisplayInfoImage(int displayInfoId);

	public List<ProductImage> selectProductImages(int productId);

	public List<ProductPrice> selectProductPrices(int productId);

	public List<Comment> selectComments(int productId);

	public List<CommentImage> selectCommentImages(int commentId);
}
