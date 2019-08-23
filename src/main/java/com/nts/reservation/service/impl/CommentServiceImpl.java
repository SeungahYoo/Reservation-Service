package com.nts.reservation.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	@Transactional
	public void saveComment(Comment comment, List<MultipartFile> multipartFiles) throws IOException {
		commentMapper.insertUserComment(comment);

		List<CommentImage> commentImages = uploadCommentImages(multipartFiles);

		if (commentImages.size() > 0) {
			commentMapper.insertFileInfo(commentImages);

			for (CommentImage commentImage : commentImages) {
				commentMapper.insertUserCommentImage(comment.getReservationInfoId(), comment.getCommentId(),
					commentImage.getFileId());
			}
		}

	}

	private List<CommentImage> uploadCommentImages(List<MultipartFile> multipartFiles) throws IOException {
		List<CommentImage> commentImageList = new ArrayList<>();

		for (MultipartFile multiPartFile : multipartFiles) {
			if (multiPartFile.isEmpty()) {
				continue;
			}
			CommentImage commentImage = getCommentImage(multiPartFile);

			fileIOHelper.uploadFile(multiPartFile, "comment_img/" + commentImage.getFileName());

			commentImageList.add(commentImage);
		}

		return commentImageList;
	}

	private CommentImage getCommentImage(MultipartFile multiPartFile) {
		CommentImage commentImage = new CommentImage();

		String saveFileName = getSaveFileName(multiPartFile.getOriginalFilename());
		commentImage.setContentType(multiPartFile.getContentType());
		commentImage.setFileName(saveFileName);
		commentImage.setSaveFileName(SAVE_PATH + "comment_img/" + saveFileName);

		return commentImage;
	}

	private String getSaveFileName(String fileName) {
		return UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
	}
}
