package com.nts.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.reservation.dao.PromotionDao;
import com.nts.reservation.dto.Promotion;
import com.nts.reservation.service.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {
	@Autowired
	PromotionDao promotionDao;

	@Override
	@Transactional
	public List<Promotion> getPromotionImages() {
		return promotionDao.selectAll();
	}

}
