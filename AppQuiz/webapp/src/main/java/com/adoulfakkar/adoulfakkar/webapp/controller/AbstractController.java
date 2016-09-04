package com.adoulfakkar.quizzApp.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.adoulfakkar.quizzApp.db.model.User;
import com.adoulfakkar.quizzApp.webapp.vo.AbstractResponse;
import com.adoulfakkar.quizzApp.webapp.vo.ErrorResponse;

public class AbstractController {

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public @ResponseBody ErrorResponse onFunctionnalError(Exception exception) {
		System.out.println("functionnal error");
		exception.printStackTrace();
		
		ErrorResponse result = new ErrorResponse();
		result.setResult(AbstractResponse.RESPONSE_RESULT_KO);
		result.setError(exception.getMessage());
		
		return result;
	}
	
	protected User getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		}
		return null;
	}
	
}
