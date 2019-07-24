package com.nts.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nts.reservation.dto.Promotion;
import com.nts.reservation.service.PromotionService;

@RestController
@RequestMapping(path = "/api")
public class PromotionApiController {
	private PromotionService promotionService;

	@Autowired
	public PromotionApiController(PromotionService promotionService) {
		this.promotionService = promotionService;
	}

	@GetMapping("/promotions")
	public List<Promotion> getPromotions() {
		return promotionService.getPromotions();
	}

}