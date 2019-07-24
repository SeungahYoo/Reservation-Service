package com.nts.reservation.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Promotion;

@Repository
public class PromotionDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Promotion> rowMapper = BeanPropertyRowMapper.newInstance(Promotion.class);
	private static final String SELECT_PROMOTIONS = "SELECT save_file_name promotion_image FROM file_info WHERE id IN "
		+ "(SELECT file_id FROM product_image WHERE product_id IN"
		+ "(SELECT id FROM product WHERE id IN "
		+ "(SELECT product_id FROM promotion)) AND type='th')";

	@Autowired
	public PromotionDao(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<Promotion> selectPromotions() {
		return jdbc.query(SELECT_PROMOTIONS, Collections.emptyMap(), rowMapper);
	}
}
