package com.nts.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nts.reservation.dto.Category;
import com.nts.reservation.service.CategoryService;

@RestController
@RequestMapping(path = "/api")
public class CategoryApiController {
	CategoryService categoryService;

	@Autowired
	public CategoryApiController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/categories")
	public List<Category> getCategories() {
		System.out.println(categoryService.getCategories());
		return categoryService.getCategories();
	}

}