package com.nts.reservation.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Product;

@Repository
public interface ProductMapper {
	public List<Product> selectProducts(Map<String, Object> parameters);
}
