package com.nts.reservation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nts.reservation.service.DisplayInfoService;

@RestController
@RequestMapping(path = "/api")
public class DisplayInfoApiController {
	private final DisplayInfoService displayInfoService;

	@Autowired
	public DisplayInfoApiController(DisplayInfoService displayInfoService) {
		this.displayInfoService = displayInfoService;
	}

	@GetMapping("/detail")
	public Map<String, Object> getDisplayInfo(@RequestParam(name = "id") int displayInfoId,
		@RequestParam(name = "is-detail") boolean isDetail) {
		return this.displayInfoService.getDisplayInfoByID(displayInfoId, isDetail);
	}

}
