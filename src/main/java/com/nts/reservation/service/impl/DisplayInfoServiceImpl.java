package com.nts.reservation.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dto.DisplayInfo;
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
	public Map<String, Object> getDisplayInfoByID(int displayInfoId) {

		DisplayInfo displayInfo = displayInfoMapper.selectDisplayInfo(displayInfoId);
		int productId = displayInfo.getProductId();

		Map<String, Object> displayMap = new HashMap<>();
		displayMap.put("displayInfo", displayInfo);
		displayMap.put("displayInfoImage", productMapper.selectComments(productId));
		displayMap.put("productImages", productMapper.selectProductImages(productId));
		displayMap.put("comments", productMapper.selectComments(productId));
		System.out.println(productMapper.selectComments(productId));
		return displayMap;
	}

}
