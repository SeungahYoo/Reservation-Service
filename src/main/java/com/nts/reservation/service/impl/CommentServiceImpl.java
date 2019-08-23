package com.nts.reservation.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;
import com.nts.reservation.mapper.CommentMapper;
import com.nts.reservation.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentMapper commentMapper;

	public CommentServiceImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;

	}

	@Override
	@Transactional
	public void saveComment(Comment comment, List<CommentImage> commentImages) throws IOException {
		commentMapper.insertUserComment(comment);

		if (commentImages.isEmpty()) {
			return;
		}

		commentMapper.insertFileInfo(commentImages);

		for (CommentImage commentImage : commentImages) {
			commentImage.setReservationInfoId(comment.getReservationInfoId());
			commentImage.setReservationUserCommentId(comment.getCommentId());
			commentMapper.insertUserCommentImage(commentImage);
		}

	}

}
