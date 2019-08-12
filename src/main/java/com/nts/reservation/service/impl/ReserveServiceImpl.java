package com.nts.reservation.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.ReservationParam;
import com.nts.reservation.mapper.DisplayInfoMapper;
import com.nts.reservation.mapper.ProductMapper;
import com.nts.reservation.mapper.ReserveMapper;
import com.nts.reservation.service.ReserveService;

@Service
public class ReserveServiceImpl implements ReserveService {
	private final DisplayInfoMapper displayInfoMapper;
	private final ProductMapper productMapper;
	private final ReserveMapper reserveMapper;

	public ReserveServiceImpl(DisplayInfoMapper displayInfoMapper, ProductMapper productMapper,
		ReserveMapper reserveMapper) {
		this.displayInfoMapper = displayInfoMapper;
		this.productMapper = productMapper;
		this.reserveMapper = reserveMapper;
	}

	@Override
	public Map<String, Object> getReserveInfo(int displayInfoId) {
		DisplayInfo displayInfo = displayInfoMapper.selectDisplayInfo(displayInfoId);
		if (displayInfo == null) {
			throw new DataRetrievalFailureException(
				"The expedted data could not be retrieved. displayInfoId: " + displayInfoId);
		}
		int productId = displayInfo.getProductId();

		Map<String, Object> displayMap = new HashMap<>();
		displayMap.put("displayInfo", displayInfo);
		displayMap.put("productImages", productMapper.selectProductImages(productId));
		displayMap.put("productPrices", productMapper.selectProductPrices(productId));

		return displayMap;
	}

	@Override
	public void saveReserveInfo(ReservationParam reservationParam) {
		reserveMapper.insertReserveInfo(reservationParam);
	}

}
