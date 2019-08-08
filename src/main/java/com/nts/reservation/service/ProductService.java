package com.nts.reservation.service;

import java.util.List;

import com.nts.reservation.dto.Product;

public interface ProductService {
	List<Product> getProducts(int categoryId, int startIndex, int maxCount, String imageType);
}
