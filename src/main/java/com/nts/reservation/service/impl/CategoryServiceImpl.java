package com.nts.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dto.Category;
import com.nts.reservation.mapper.CategoryMapper;
import com.nts.reservation.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	private CategoryMapper categoryMapper;

	@Autowired
	public CategoryServiceImpl(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}

	@Override
	public List<Category> getCategories() {
		System.out.println(categoryMapper.selectCategories());
		return categoryMapper.selectCategories();
	}

	@Override
	public int getCategoryCoount(int categoryId) {
		System.out.println("count" + categoryMapper.selectCategoryCount(categoryId));
		return categoryMapper.selectCategoryCount(categoryId);
	}

}