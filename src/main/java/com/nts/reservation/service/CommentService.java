package com.nts.reservation.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nts.reservation.dto.Comment;

public interface CommentService {
	void saveComment(Comment comment, List<MultipartFile> commentImages) throws IOException;
}
