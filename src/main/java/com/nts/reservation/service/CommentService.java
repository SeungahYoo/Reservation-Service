package com.nts.reservation.service;

import java.io.IOException;
import java.util.List;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;

public interface CommentService {
	void saveComment(Comment comment, List<CommentImage> commentImages) throws IOException;
}
