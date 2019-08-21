package com.nts.reservation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;

public interface CommentMapper {
	void insertUserComment(Comment comment);

	void insertFileInfo(@Param("commentImages") List<CommentImage> commentImages);
}
