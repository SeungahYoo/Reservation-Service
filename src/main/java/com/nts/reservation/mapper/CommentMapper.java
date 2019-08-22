package com.nts.reservation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentImage;

@Repository
public interface CommentMapper {
	void insertUserComment(Comment comment);

	void insertFileInfo(@Param("commentImages") List<CommentImage> commentImages);

	void insertUserCommentImage(@Param("reservationInfoId") int ReservationInfoId,
		@Param("reservationUserCommentId") int reservationUserCommentId,
		@Param("fileId") int fileId);
}
