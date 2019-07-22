package com.nts.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.nts.reservation.dto.Promotion;
import com.nts.reservation.service.PromotionService;

@Controller
public class PromotionController {
	PromotionService promotionService;

	@Autowired
	public PromotionController(PromotionService promotionService) {
		this.promotionService = promotionService;
	}

	@GetMapping(path = "/mainpage")
	public String mainpage(ModelMap model) {
		//promotion image 

		List<Promotion> promotionImages = promotionService.getPromotionImages();

		model.addAttribute("promotionImages", promotionImages);

		return "mainpage";
	}
}
