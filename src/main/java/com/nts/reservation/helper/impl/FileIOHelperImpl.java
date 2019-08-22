package com.nts.reservation.helper.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nts.reservation.helper.FileIOHelper;

@Service
public class FileIOHelperImpl implements FileIOHelper {
	private static final String SAVE_PATH = "c:/tmp/";

	@Override
	public void downloadFile(HttpServletResponse response, String saveFileName) throws IOException {
		String saveFilePath = SAVE_PATH + saveFileName; // 맥일 경우 "/tmp/connect.png" 로 수정
		File saveFile = new File(saveFilePath);

		response.setHeader("Content-Disposition",
			"attachment; filename=\"" + Paths.get(saveFilePath).getFileName() + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", Files.probeContentType(Paths.get(saveFilePath)));
		response.setHeader("Content-Length", "" + saveFile.length());
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		FileCopyUtils.copy(FileUtils.openInputStream(saveFile), response.getOutputStream());
	}

	@Override
	public void uploadFile(MultipartFile multipartFile, String saveFileName) throws IOException {
		FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(SAVE_PATH + saveFileName));

	}

	//	@Override
	//	private void uploadFile(MultipartFile multipartFile, String saveFileName) throws IOException {
	//		byte[] data = ;
	//
	//		FileOutputStream fileOutputStream = new FileOutputStream(SAVE_PATH + saveFileName);
	//		fileOutputStream.write(data);
	//		fileOutputStream.close();
	//
	//
	//	}
	//

}
