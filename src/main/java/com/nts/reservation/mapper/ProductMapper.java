package com.nts.reservation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Product;

@Repository
public interface ProductMapper {
	public List<Product> selectProducts(@Param("categoryId") int categoryId, @Param("startIndex") int startIndex,
		@Param("maxCount") int maxCount);
}
