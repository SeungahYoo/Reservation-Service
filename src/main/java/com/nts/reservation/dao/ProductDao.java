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
	private static final String SELECT_ALL = "SELECT p.category_id, p.description as title, p.content, di.place_name as place, fi.save_file_name as imgname "
		+ "FROM product AS p JOIN display_info AS di on p.id = di.product_id "
		+ "JOIN product_image AS pi on p.id=pi.product_id "
		+ "JOIN file_info AS fi on pi.file_id = fi.id "
		+ "WHERE(p.category_id = :categoryId and pi.type='th') "
		+ "LIMIT :start , :limit";

	@Autowired
	public ProductDao(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<Product> selectProductsFromStartIdx(int categoryId, int startIdx, int displayLimit) {
		Map<String, Integer> parameter = new HashMap<>();

		if (categoryId <= 0) {

		}

		parameter.put("categoryId", categoryId);
		parameter.put("start", startIdx);
		parameter.put("limit", displayLimit);

		return jdbc.query(SELECT_ALL, parameter, rowMapper);
	}
}
