package com.nts.reservation.helper;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface FileIOHelper {
	void downloadFile(HttpServletResponse response, String saveFileName) throws IOException;
}
