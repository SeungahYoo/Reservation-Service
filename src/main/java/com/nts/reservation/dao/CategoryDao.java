package com.nts.reservation.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Category;

@Repository
public class CategoryDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Category> rowMapper = BeanPropertyRowMapper.newInstance(Category.class);
	private static final String SELECT_CATEGORIES = "SELECT id, name FROM category";
	private static final String SELECT_CATEGORY_COUNT = "SELECT count(*) product_count FROM product "
		+ "JOIN display_info ON product.id = display_info.product_id "
		+ "WHERE(${dynamicQuery})";

	@Autowired
	public CategoryDao(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<Category> selectCategories() {
		return jdbc.query(SELECT_CATEGORIES, Collections.emptyMap(), rowMapper);
	}

	public int selectCategoryCount(int categoryId) {
		String dynamicQuery = "product.category_id";

		if (categoryId > 0) {
			dynamicQuery += "=" + categoryId;
		}
		String new_SELECT_CATEGORY_COUNT = SELECT_CATEGORY_COUNT.replace("${dynamicQuery}", dynamicQuery);
		return jdbc.queryForObject(new_SELECT_CATEGORY_COUNT, Collections.emptyMap(), Integer.class);
	}
}
