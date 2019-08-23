package com.nts.reservation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;
import com.nts.reservation.dto.Product;
import com.nts.reservation.dto.ProductImage;
import com.nts.reservation.dto.ProductPrice;

@Repository
public interface ProductMapper {
	List<Product> selectProducts(Map<String, Object> parameters);

	List<ProductImage> selectProductImages(int productId);

	List<ProductPrice> selectProductPrices(int productId);

	List<Comment> selectComments(@Param("productId") int productId, @Param("isDetail") boolean isDetail);

	List<CommentImage> selectCommentImages(int commentId);

	Product selectProductByProductId(int productId);
}
