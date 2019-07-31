package com.nts.reservation.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.DisplayInfoImage;
import com.nts.reservation.mapper.DisplayInfoMapper;
import com.nts.reservation.service.DisplayInfoService;

@Service
public class DisplayInfoServiceImpl implements DisplayInfoService {
	private final DisplayInfoMapper displayInfoMapper;

	@Autowired
	public DisplayInfoServiceImpl(DisplayInfoMapper displayInfoMapper) {
		this.displayInfoMapper = displayInfoMapper;
	}

	@Override
	public DisplayInfo getDisplayInfo(@Param("displayInfoId") int displayInfoId) {
		return displayInfoMapper.selectDisplayInfo(displayInfoId);
	}

	@Override
	public DisplayInfoImage getDisplayInfoImage(@Param("displayInfoId") int displayInfoId) {
		return displayInfoMapper.selectDisplayInfoImage(displayInfoId);
	}

}
