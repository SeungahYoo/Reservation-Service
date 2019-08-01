package com.nts.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nts.reservation.dto.Product;
import com.nts.reservation.service.ProductService;

@RestController
@RequestMapping(path = "/api")
public class ProductApiController {
	private final ProductService productService;
	private static final int MAX_COUNT = 4;

	@Autowired
	public ProductApiController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public List<Product> getProducts(
		@RequestParam(name = "categoryId", required = false, defaultValue = "0") int categoryId,
		@RequestParam(name = "startIndex", required = false, defaultValue = "0") int startIndex,
		@RequestParam(name = "imageType", required = false, defaultValue = "th") String imageType) {

		return productService.getProducts(categoryId, startIndex, MAX_COUNT, imageType);
	}
}
