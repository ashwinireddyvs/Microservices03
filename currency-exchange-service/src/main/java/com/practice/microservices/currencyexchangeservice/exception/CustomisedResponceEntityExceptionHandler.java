package com.practice.microservices.currencyexchangeservice.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomisedResponceEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
		
		ErrorDetails errorDetails = new ErrorDetails(
				LocalDate.now(), 
				ex.getMessage(), 
				request.getDescription(false));
		
	if(ex.getClass()==CurrencyConverionNotFoundException.class) 
		return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
	return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
		}
}
