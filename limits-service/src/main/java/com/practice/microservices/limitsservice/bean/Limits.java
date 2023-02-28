package com.practice.microservices.limitsservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Limits {
	@NonNull
	private int minimum;
	
	@NonNull
	private int maximum;
}
