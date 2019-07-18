package com.nts.reservation.service.impl;


import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nts.reservation.config.ApplicationConfig;
import com.nts.reservation.dto.Promotion;
import com.nts.reservation.service.PromotionService;

public class PromotionServiceTest {
	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		PromotionService promotionService = ac.getBean(PromotionService.class);
		
		Promotion promotion = new Promotion();
		List<Promotion> list = promotionService.getPromotionImages();
		
		for(Promotion p : list) System.out.println(p);
		
	}
}
