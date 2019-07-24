package com.nts.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dao.ProductDao;
import com.nts.reservation.dto.Product;
import com.nts.reservation.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	ProductDao productDao;

	@Autowired
	public ProductServiceImpl(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public List<Product> getProductsFromStartIdx(int categoryId, int startIdx, int displayLimit) {
		return productDao.selectProductsFromStartIdx(categoryId, startIdx, displayLimit);
	}

}
