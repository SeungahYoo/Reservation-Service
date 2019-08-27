package com.nts.reservation.mapper;

import org.springframework.stereotype.Repository;

import com.nts.reservation.dto.FileInfo;

@Repository
public interface FileInfoMapper {
	FileInfo selectFileInfo(int fileId);
}
