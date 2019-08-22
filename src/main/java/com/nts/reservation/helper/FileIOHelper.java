package com.nts.reservation.helper;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface FileIOHelper {
	void downloadFile(HttpServletResponse response, String saveFileName) throws IOException;

	void uploadFile(MultipartFile multipartFile, String saveFileName) throws IOException;
}
