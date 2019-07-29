package com.nts.reservation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nts.reservation.dto.Category;

public interface CategoryMapper {
	public List<Category> selectCategories();

	public int selectCategoryCount(@Param("categoryId") int categoryId);
}
