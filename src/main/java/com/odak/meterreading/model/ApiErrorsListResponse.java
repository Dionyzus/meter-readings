package com.odak.meterreading.model;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiErrorsListResponse {

	private HttpStatus status;
	private List<String> errorMessages;
}
