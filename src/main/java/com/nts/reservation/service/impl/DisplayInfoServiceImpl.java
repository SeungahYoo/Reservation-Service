package com.nts.reservation.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.Product;
import com.nts.reservation.mapper.DisplayInfoMapper;
import com.nts.reservation.mapper.ProductMapper;
import com.nts.reservation.service.DisplayInfoService;

@Service
public class DisplayInfoServiceImpl implements DisplayInfoService {
	private final DisplayInfoMapper displayInfoMapper;
	private final ProductMapper productMapper;

	@Autowired
	public DisplayInfoServiceImpl(DisplayInfoMapper displayInfoMapper, ProductMapper productMapper) {
		this.displayInfoMapper = displayInfoMapper;
		this.productMapper = productMapper;
	}

	@Override
	public Map<String, Object> getDisplayInfoByID(int displayInfoId, boolean isDetail) {
		DisplayInfo displayInfo = displayInfoMapper.selectDisplayInfo(displayInfoId);
		if (displayInfo == null) {
			throw new DataRetrievalFailureException(
				"The expedted data could not be retrieved. displayInfoId: " + displayInfoId);
		}
		int productId = displayInfo.getProductId();

		Map<String, Object> displayMap = new HashMap<>();
		displayMap.put("displayInfo", displayInfo);
		displayMap.put("displayInfoImage", displayInfoMapper.selectDisplayInfoImage(displayInfoId));
		displayMap.put("productImages", productMapper.selectProductImages(productId));
		displayMap.put("productPrices", productMapper.selectProductPrices(productId));
		displayMap.put("comments", productMapper.selectComments(productId, isDetail));

		Product product = productMapper.selectProductByProductId(productId);
		displayMap.put("productScoreAverage", product.getProductScoreAverageView());
		displayMap.put("commentsCount", product.getCommentsCount());
		return displayMap;
	}

}
