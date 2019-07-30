package com.nts.reservation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dto.Product;
import com.nts.reservation.mapper.ProductMapper;
import com.nts.reservation.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductMapper productMapper;

	@Autowired
	public ProductServiceImpl(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	@Override
	public List<Product> getProducts(@Param("categoryId") int categoryId, @Param("startIndex") int startIndex,
		@Param("maxCount") int maxCount, @Param("imageType") String imageType) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("categoryId", categoryId);
		parameters.put("startIndex", startIndex);
		parameters.put("maxCount", maxCount);
		parameters.put("imageType", imageType);
		return productMapper.selectProducts(parameters);
	}
}
