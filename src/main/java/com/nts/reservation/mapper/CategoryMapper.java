package com.nts.reservation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Category;

@Repository
public interface CategoryMapper {
	List<Category> selectCategories();

	int selectCategoryCount(@Param("categoryId") int categoryId);
}
