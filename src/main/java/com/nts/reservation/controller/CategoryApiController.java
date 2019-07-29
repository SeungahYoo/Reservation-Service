package com.nts.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nts.reservation.dto.Category;
import com.nts.reservation.service.CategoryService;

@RestController
@RequestMapping(path = "/api")
public class CategoryApiController {
	private CategoryService categoryService;

	@Autowired
	public CategoryApiController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/categories")
	public List<Category> getCategories() {
		return categoryService.getCategories();
	}

	@GetMapping("/categories/count")
	public int getCategoryCount(
		@RequestParam(name = "categoryId", required = false, defaultValue = "0") int categoryId) {
		System.out.println("categoryController " + categoryService.getCategoryCount(categoryId));
		return categoryService.getCategoryCount(categoryId);
	}

}