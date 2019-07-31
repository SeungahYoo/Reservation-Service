package com.nts.reservation.service;

import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.DisplayInfoImage;

public interface DisplayInfoService {
	DisplayInfo getDisplayInfo(int displayInfoId);

	DisplayInfoImage getDisplayInfoImage(int displayInfoId);
}
