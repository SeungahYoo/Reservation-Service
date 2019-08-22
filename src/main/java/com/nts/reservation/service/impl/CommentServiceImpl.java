package com.nts.reservation.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;
import com.nts.reservation.mapper.CommentMapper;
import com.nts.reservation.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	private static final String SAVE_PATH = "c:/tmp/";
	private static final String PREFIX_URL = "c:/tmp/";

	private final CommentMapper commentMapper;

	public CommentServiceImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Override
	public void saveComment(Comment comment, List<MultipartFile> multipartFiles) {
		List<CommentImage> commentImages = uploadCommentImages(multipartFiles);

		commentMapper.insertUserComment(comment);
		commentMapper.insertFileInfo(commentImages);
		for (CommentImage commentImage : commentImages) {
			commentMapper.insertUserCommentImage(comment.getReservationInfoId(), comment.getCommentId(),
				commentImage.getFileId());
		}

	}

	private List<CommentImage> uploadCommentImages(List<MultipartFile> multipartFiles) {
		List<CommentImage> commentImageList = new ArrayList<>();

		for (MultipartFile multiPartFile : multipartFiles) {
			try {
				String originFileName = multiPartFile.getOriginalFilename();

				if (StringUtils.isBlank(originFileName)) {
					continue;
				}

				String saveFileName = getSaveFileName(originFileName);
				writeFile(multiPartFile, saveFileName);

				CommentImage commentImage = new CommentImage();

				commentImage.setContentType(multiPartFile.getContentType());
				commentImage.setFileName(saveFileName);
				commentImage.setSaveFileName(SAVE_PATH + "commentImage/" + saveFileName);

				commentImageList.add(commentImage);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return commentImageList;
	}

	private boolean writeFile(MultipartFile multipartFile, String saveFileName) throws IOException {
		boolean result = false;
		byte[] data = multipartFile.getBytes();

		FileOutputStream fileOutputStream = new FileOutputStream(SAVE_PATH + saveFileName);
		fileOutputStream.write(data);
		fileOutputStream.close();

		return result;
	}

	private String getSaveFileName(String fileName) {
		Calendar calendar = Calendar.getInstance();

		return calendar.get(Calendar.YEAR)
			+ calendar.get(Calendar.MONTH)
			+ calendar.get(Calendar.DATE)
			+ calendar.get(Calendar.HOUR)
			+ calendar.get(Calendar.MINUTE)
			+ calendar.get(Calendar.SECOND)
			+ calendar.get(Calendar.MILLISECOND)
			+ fileName.substring(fileName.lastIndexOf("."), fileName.length());
	}
}
