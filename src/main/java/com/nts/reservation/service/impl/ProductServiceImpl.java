package com.nts.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dto.Product;
import com.nts.reservation.mapper.ProductMapper;
import com.nts.reservation.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	private ProductMapper productMapper;

	@Autowired
	public ProductServiceImpl(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	@Override
	public List<Product> getProducts(int categoryId, int startIndex, int maxCount) {
		return productMapper.selectProducts(categoryId, startIndex, maxCount);
	}

}
