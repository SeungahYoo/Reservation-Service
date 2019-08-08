package com.nts.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dto.Category;
import com.nts.reservation.mapper.CategoryMapper;
import com.nts.reservation.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	private final CategoryMapper categoryMapper;

	@Autowired
	public CategoryServiceImpl(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}

	@Override
	public List<Category> getCategories() {
		return categoryMapper.selectCategories();
	}

	@Override
	public int getCategoryCount(int categoryId) {
		return categoryMapper.selectCategoryCount(categoryId);
	}

}