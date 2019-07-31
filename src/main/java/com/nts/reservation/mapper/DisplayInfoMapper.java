package com.nts.reservation.mapper;

import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.DisplayInfoImage;

@Repository
public interface DisplayInfoMapper {
	public DisplayInfo selectDisplayInfo(int displayInfoId);

	public DisplayInfoImage selectDisplayInfoImage(int displayInfoId);
}
