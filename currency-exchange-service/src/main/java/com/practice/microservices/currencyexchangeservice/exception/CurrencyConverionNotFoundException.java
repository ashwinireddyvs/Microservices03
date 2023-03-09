package com.practice.microservices.currencyexchangeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class CurrencyConverionNotFoundException extends RuntimeException{
	public CurrencyConverionNotFoundException(String Message) {
		super(Message);
	}
}
