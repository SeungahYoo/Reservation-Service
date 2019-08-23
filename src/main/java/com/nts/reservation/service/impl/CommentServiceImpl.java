package com.nts.reservation.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
				commentImage.setReservationInfoId(comment.getReservationInfoId());
				commentImage.setReservationUserCommentId(comment.getCommentId());
				commentMapper.insertUserCommentImage(commentImage);
			}
		}

	}

	private List<CommentImage> uploadCommentImages(List<MultipartFile> multipartFiles) {
		List<CommentImage> commentImageList = null;

		commentImageList = multipartFiles.stream()
			.filter(multipartFile -> !multipartFile.isEmpty())
			.map(multipartFile -> {
				CommentImage commentImage = getCommentImage(multipartFile);
				fileUpload(multipartFile, commentImage.getFileName());
				return commentImage;
			})
			.collect(Collectors.toList());

		return commentImageList;
	}

	private void fileUpload(MultipartFile multipartFile, String fileName) {
		try {
			fileIOHelper.uploadFile(multipartFile, "comment_img/" + fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
