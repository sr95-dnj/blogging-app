package com.blogging.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler
	public String handleException(Exception exception, Model model) {

		String message = exception != null ? exception.getLocalizedMessage() : "Something Went Wrong";
		logger.error("Exception : ", exception);
		model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
		model.addAttribute("message", message);

		return "error";
	}

}
