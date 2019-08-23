package com.nts.reservation.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.service.CommentService;

@RestController
@RequestMapping(path = "/api")
public class CommentApiController {
	private final CommentService commentService;

	public CommentApiController(CommentService commentService) {
		this.commentService = commentService;
	}

	@Transactional
	@PostMapping("comments")
	public ModelAndView saveComment(Comment comment, @RequestParam("files") List<MultipartFile> commentImages)
		throws IOException {
		commentService.saveComment(comment, commentImages);
		return new ModelAndView("redirect:/user-check");
	}
}
