package com.nts.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nts.reservation.dto.Promotion;
import com.nts.reservation.service.PromotionService;

@RestController
@RequestMapping(path = "/api")
public class PromotionApiController {
	@Autowired
	PromotionService promotionService;

	@GetMapping("/promotions")
	public Map<String, Object> list() {
		List<Promotion> list = promotionService.getPromotionImages();

		Map<String, Object> map = new HashMap<>();
		map.put("list", list);

		return map;
	}

}