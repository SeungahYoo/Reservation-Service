package com.nts.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.DisplayInfo;
import com.nts.reservation.dto.DisplayInfoImage;
import com.nts.reservation.dto.ProductImage;
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
	public Map<String, Object> getDisplayInfo(@RequestParam(name = "id") int displayInfoId) {
		Map<String, Object> displayInfoMap = new HashMap<>();

		DisplayInfo displayInfo = displayInfoService.getDisplayInfo(displayInfoId);
		DisplayInfoImage displayInfoImage = displayInfoService.getDisplayInfoImage(displayInfoId);

		int productId = displayInfo.getProductId();
		List<ProductImage> productImages = displayInfoService.getProductImages(productId);
		//List<ProductPrice> productPrices = displayInfoService.getProductPrices(productId);
		List<Comment> comments = displayInfoService.getComments(productId);
		displayInfoMap.put("displayInfo", displayInfo);
		displayInfoMap.put("displayInfoImage", displayInfoImage);
		displayInfoMap.put("productImages", productImages);
		//displayInfoMap.put("productPrices", productPrices);
		displayInfoMap.put("comments", comments);

		return displayInfoMap;
	}

}
