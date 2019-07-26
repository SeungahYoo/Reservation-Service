package com.nts.reservation.mapper;

import java.util.List;

import com.nts.reservation.dto.Category;

public interface CategoryMapper {
	public List<Category> selectCategories();

	public int selectCategoryCount(int categoryId);
}
