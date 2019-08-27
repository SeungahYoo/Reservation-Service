package com.nts.reservation.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;
import com.nts.reservation.helper.FileIOHelper;
import com.nts.reservation.service.CommentService;

@RestController
@RequestMapping(path = "/api")
public class CommentApiController {
	private final CommentService commentService;
	private final FileIOHelper fileIOHelper;

	public CommentApiController(CommentService commentService, FileIOHelper fileIOHelper) {
		this.commentService = commentService;
		this.fileIOHelper = fileIOHelper;
	}

	@PostMapping("comments")
	public ModelAndView saveComment(Comment comment, @RequestParam("files") List<MultipartFile> multipartFiles)
		throws IOException {

		List<CommentImage> commentImages = uploadCommentImages(multipartFiles);

		commentService.saveComment(comment, commentImages);
		return new ModelAndView("redirect:/user-check");
	}

	private List<CommentImage> uploadCommentImages(List<MultipartFile> multipartFiles) {
		List<CommentImage> commentImageList = null;

		commentImageList = multipartFiles.stream()
			.filter(multipartFile -> !multipartFile.isEmpty())
			.map(multipartFile -> {
				CommentImage commentImage = getCommentImage(multipartFile);
				fileUpload(multipartFile, commentImage.getSaveFileName());
				return commentImage;
			})
			.collect(Collectors.toList());

		return commentImageList;
	}

	private void fileUpload(MultipartFile multipartFile, String saveFileName) {

		try {
			fileIOHelper.uploadFile(multipartFile, saveFileName);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

	}

	private CommentImage getCommentImage(MultipartFile multiPartFile) {
		CommentImage commentImage = new CommentImage();

		String saveFileName = getSaveFileName(multiPartFile.getOriginalFilename());
		commentImage.setContentType(multiPartFile.getContentType());
		commentImage.setFileName(multiPartFile.getOriginalFilename());
		commentImage.setSaveFileName("comment_img/" + saveFileName);

		return commentImage;
	}

	private String getSaveFileName(String fileName) {
		return UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
	}
}
