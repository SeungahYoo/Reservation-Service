package com.nts.reservation.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;
import com.nts.reservation.helper.FileIOHelper;
import com.nts.reservation.mapper.CommentMapper;
import com.nts.reservation.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	private static final String SAVE_PATH = "c:/tmp/";

	private final CommentMapper commentMapper;
	private final FileIOHelper fileIOHelper;

	public CommentServiceImpl(CommentMapper commentMapper, FileIOHelper fileIOHelper) {
		this.commentMapper = commentMapper;
		this.fileIOHelper = fileIOHelper;
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

				fileIOHelper.uploadFile(multiPartFile, "comment_img/" + saveFileName);

				CommentImage commentImage = new CommentImage();

				commentImage.setContentType(multiPartFile.getContentType());
				commentImage.setFileName(saveFileName);
				commentImage.setSaveFileName(SAVE_PATH + "comment_img/" + saveFileName);

				commentImageList.add(commentImage);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return commentImageList;
	}

	private String getSaveFileName(String fileName) {
		return UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
	}
}
