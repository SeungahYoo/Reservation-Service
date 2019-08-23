package com.nts.reservation.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Promotion;

@Repository
public interface PromotionMapper {
	List<Promotion> selectPromotions();
}
