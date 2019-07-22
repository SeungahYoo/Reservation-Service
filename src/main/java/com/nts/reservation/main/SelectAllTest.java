package com.nts.reservation.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nts.reservation.config.ApplicationConfig;
import com.nts.reservation.dao.PromotionDao;
import com.nts.reservation.dto.Promotion;

public class SelectAllTest {
	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		PromotionDao promotionDao = ac.getBean(PromotionDao.class);
		List<Promotion> list = promotionDao.selectAll();

		for (Promotion promotion : list) {
			System.out.println(promotion);
		}
	}
}
