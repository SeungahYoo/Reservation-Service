package com.nts.reservation.service;

import java.util.List;

import com.nts.reservation.dto.Product;

public interface ProductService {
	List<Product> getProductsFromStartIdx(int categoryId, int startIdx, int displayLimit);
}
