package com.nts.reservation.service;

import java.util.Map;

public interface DisplayInfoService {
	Map<String, Object> getDisplayInfoByID(int displayInfoId, boolean isDetail);
}
