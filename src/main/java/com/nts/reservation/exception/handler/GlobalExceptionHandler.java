package com.nts.reservation.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
	public String handleMethodArgumentTypeMismatch(Exception e) {
		LOGGER.error(e.getMessage(), e);
		return "mainpage";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({DataRetrievalFailureException.class})
	public void handleResourceNotFoundException(Exception e) {
		LOGGER.error(e.getMessage(), e);
	}
}
