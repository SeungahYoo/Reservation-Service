package com.nts.reservation.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nts.reservation.helper.FileIOHelper;

@RestController
@RequestMapping(path = "/file")
public class FileIOController {
	private final FileIOHelper fileIOHelper;

	public FileIOController(FileIOHelper fileIOHelper) {
		this.fileIOHelper = fileIOHelper;
	}

	@GetMapping("download")
	public void downloadFile(HttpServletResponse response, @RequestParam(name = "fileId") int fileId)
		throws IOException {
		fileIOHelper.downloadFile(response, fileId);
	}
}
