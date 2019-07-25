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
	private static final String SELECT_PROMOTIONS = "SELECT save_file_name promotion_image FROM file_info "
		+ "JOIN product_image ON file_info.id=product_image.file_id "
		+ "JOIN product ON product_image.product_id=product.id "
		+ "JOIN promotion ON product.id=promotion.product_id "
		+ "WHERE(product_image.type='th')";
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Promotion> rowMapper = BeanPropertyRowMapper.newInstance(Promotion.class);

	@Autowired
	public PromotionDao(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<Promotion> selectPromotions() {
		return jdbc.query(SELECT_PROMOTIONS, Collections.emptyMap(), rowMapper);
	}
}
