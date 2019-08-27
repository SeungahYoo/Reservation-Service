package com.nts.reservation.mapper;

import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.DisplayInfoImage;

@Repository
public interface DisplayInfoMapper {
	DisplayInfo selectDisplayInfo(int displayInfoId);

	DisplayInfoImage selectDisplayInfoImage(int displayInfoId);
}
