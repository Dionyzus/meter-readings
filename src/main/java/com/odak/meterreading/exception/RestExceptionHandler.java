package com.odak.meterreading.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.RollbackException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.odak.meterreading.model.ApiError;
import com.odak.meterreading.model.ApiErrorsListResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation error");
		apiError.addValidationErrors(exception.getBindingResult().getFieldErrors());
		apiError.addValidationError(exception.getBindingResult().getGlobalErrors());

		return buildResponseEntity(apiError);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	protected ResponseEntity<Object> handlePersistenceException(final DataIntegrityViolationException ex) {

		Throwable cause = ex.getRootCause();

		if (cause instanceof SQLIntegrityConstraintViolationException) {

			SQLIntegrityConstraintViolationException consEx = (SQLIntegrityConstraintViolationException) cause;

			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
			apiError.setMessage(consEx.getMessage());
			return buildResponseEntity(apiError);
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getLocalizedMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(RollbackException.class)
	public ResponseEntity<ApiErrorsListResponse> handleNotValidException(RollbackException ex) {

		String errMessage = ex.getCause().getMessage();

		List<String> listErrMessage = getListErrMessage(errMessage);
		ApiErrorsListResponse response = ApiErrorsListResponse.builder().status(HttpStatus.BAD_REQUEST)
				.errorMessages(listErrMessage).build();

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> inputValidationException(BadRequestException exception) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(exception.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage(exception.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleMissingCategoryBadRequest(BadRequestException exception) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(exception.getMessage());
		return buildResponseEntity(apiError);
	}

	private static List<String> getListErrMessage(String msg) {

		Stream<String> stream = Arrays.stream(msg.split("\n")).filter(s -> s.contains("\t"))
				.map(s -> s.replaceAll("^([^\\{]+)\\{", "")).map(s -> s.replaceAll("[\"]", ""))
				.map(s -> s.replaceAll("=", ":")).map(s -> s.replaceAll("interpolatedMessage", "message"))
				.map(s -> s.replaceAll("\\{|\\}(, *)?", ""));

		return stream.collect(Collectors.toList());
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
