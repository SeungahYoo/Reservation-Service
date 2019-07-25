package com.nts.reservation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Product;

@Repository
public class ProductDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);
	private static final String SELECT_PRODUCTS = ""
		+ "SELECT product.category_id category, product.id id, product.description description, product.content content, display_info.place_name placeName, file_info.save_file_name fileName "
		+ "FROM product JOIN display_info ON product.id = display_info.product_id "
		+ "JOIN product_image ON product.id=product_image.product_id "
		+ "JOIN file_info ON product_image.file_id = file_info.id "
		+ "WHERE(${dynamicQuery} product_image.type='th') "
		+ "LIMIT :start , :limit; ";

	@Autowired
	public ProductDao(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<Product> selectProducts(int categoryId, int startIndex, int maxCount) {
		Map<String, Object> parameter = new HashMap<>();
		String dynamicQuery = "";
		if (categoryId > 0) {
			dynamicQuery = "product.category_id=:categoryId AND";
			parameter.put("categoryId", categoryId);
		}
		String new_SELECT_PRODUCTS = SELECT_PRODUCTS.replace("${dynamicQuery}", dynamicQuery);
		parameter.put("start", startIndex);
		parameter.put("limit", maxCount);
		return jdbc.query(new_SELECT_PRODUCTS, parameter, rowMapper);
	}
}
