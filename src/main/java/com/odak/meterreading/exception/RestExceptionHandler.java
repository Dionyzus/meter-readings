package com.odak.meterreading.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.odak.meterreading.model.ApiError;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
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

	@ExceptionHandler({ TransactionSystemException.class })
	protected ResponseEntity<Object> handlePersistenceException(final Exception ex, final WebRequest request) {

		log.error(ex.getLocalizedMessage());
		
		Throwable cause = ((TransactionSystemException) ex).getRootCause();
		if (cause instanceof ConstraintViolationException) {

			ConstraintViolationException consEx = (ConstraintViolationException) cause;
			final List<String> errors = new ArrayList<String>();
			for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {
				errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
			}

			final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, consEx.getLocalizedMessage(), errors);
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}
		final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				"error occurred");
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

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
