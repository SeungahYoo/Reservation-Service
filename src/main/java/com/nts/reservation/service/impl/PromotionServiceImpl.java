package com.nts.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dto.Promotion;
import com.nts.reservation.mapper.PromotionMapper;
import com.nts.reservation.service.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {
	private final PromotionMapper promotionMapper;

	@Autowired
	public PromotionServiceImpl(PromotionMapper promotionMapper) {
		this.promotionMapper = promotionMapper;
	}

	@Override
	public List<Promotion> getPromotions() {
		System.out.println("promotion");
		System.out.println(promotionMapper.selectPromotions());
		return promotionMapper.selectPromotions();
	}
}