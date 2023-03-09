package com.practice.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class CurrencyExchange {
	
	@NonNull
	@Id
	private long id;
	
	@NonNull
	@Column(name = "currency_from")
	private String from;
	
	@NonNull
	@Column(name = "currency_to")
	private String to;
	
	@NonNull
	private BigDecimal conversionMultiple;
	
	
	private String environment;
}
