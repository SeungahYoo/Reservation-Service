package com.nts.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;
import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.DisplayInfoImage;
import com.nts.reservation.dto.ProductImage;
import com.nts.reservation.dto.ProductPrice;
import com.nts.reservation.mapper.DisplayInfoMapper;
import com.nts.reservation.service.DisplayInfoService;

@Service
public class DisplayInfoServiceImpl implements DisplayInfoService {
	private final DisplayInfoMapper displayInfoMapper;

	@Autowired
	public DisplayInfoServiceImpl(DisplayInfoMapper displayInfoMapper) {
		this.displayInfoMapper = displayInfoMapper;
	}

	@Override
	public DisplayInfo getDisplayInfo(int displayInfoId) {
		return displayInfoMapper.selectDisplayInfo(displayInfoId);
	}

	@Override
	public DisplayInfoImage getDisplayInfoImage(int displayInfoId) {
		return displayInfoMapper.selectDisplayInfoImage(displayInfoId);
	}

	@Override
	public List<ProductImage> getProductImages(int productId) {
		return displayInfoMapper.selectProductImages(productId);
	}

	@Override
	public List<ProductPrice> getProductPrices(int productId) {
		return displayInfoMapper.selectProductPrices(productId);
	}

	@Override
	public List<Comment> getComments(int productId) {
		return displayInfoMapper.selectComments(productId);
	}

	@Override
	public List<CommentImage> getCommentImages(int commentId) {
		return displayInfoMapper.selectCommentImages(commentId);
	}

}
