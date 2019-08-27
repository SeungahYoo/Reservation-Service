package com.nts.reservation.helper.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nts.reservation.dto.FileInfo;
import com.nts.reservation.helper.FileIOHelper;
import com.nts.reservation.mapper.FileInfoMapper;

@Service
public class FileIOHelperImpl implements FileIOHelper {
	private static final String SAVE_PATH = "c:/tmp/";

	private final FileInfoMapper fileMapper;

	public FileIOHelperImpl(FileInfoMapper fileMapper) {
		this.fileMapper = fileMapper;
	}

	@Override
	public void downloadFile(HttpServletResponse response, int fileId) throws IOException {
		if (fileId <= 0) {
			throw new IllegalArgumentException("A fileId can't be below 0. fildId : " + fileId);
		}

		FileInfo fileInfo = fileMapper.selectFileInfo(fileId);

		if (fileInfo == null) {
			throw new DataRetrievalFailureException(
				"The expedted data could not be retrieved. fileId: " + fileId);
		}

		String saveFilePath = SAVE_PATH + fileInfo.getSaveFileName();
		File saveFile = new File(saveFilePath);

		response.setHeader("Content-Disposition",
			"attachment; filename=\"" + fileInfo.getFileName() + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", fileInfo.getContentType());
		response.setHeader("Content-Length", "" + saveFile.length());
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		try {
			FileCopyUtils.copy(FileUtils.openInputStream(saveFile), response.getOutputStream());
		} catch (FileNotFoundException e) {
			response.sendRedirect("/reservation/img/default.png");
		}
	}

	@Override
	public void uploadFile(MultipartFile multipartFile, String saveFileName) throws IOException {
		multipartFile.transferTo(new File(SAVE_PATH + saveFileName));
	}
}
