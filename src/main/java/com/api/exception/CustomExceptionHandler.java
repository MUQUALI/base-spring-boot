package com.api.exception;

import com.api.dto.response.ErrorResponse;
import lombok.NonNull;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getCode(), new Date(), exception.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException exception) {
		String message = NestedExceptionUtils.getMostSpecificCause(exception).getMessage();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), new Date(), message);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException exception) {
		String message = NestedExceptionUtils.getMostSpecificCause(exception).getMessage();
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), new Date(), message);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(AlreadyExistsException.class)
	public final ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistsException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getStatusCode(), new Date(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}

	@ExceptionHandler(MissingRequestParameterException.class)
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingRequestParameterException ex) {
		ErrorResponse errorResponse = new ErrorResponse(1005, new Date(), ex.getParameterName());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(CompletableException.class)
	public final ResponseEntity<ErrorResponse> handleBadRequestException(CompletableException exception) {
		ErrorResponse errorResponse = ErrorResponse.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	@ExceptionHandler(InternalServerException.class)
	public final ResponseEntity<ErrorResponse> handleInternalServerException(InternalServerException exception) {
		ErrorResponse errorResponse = ErrorResponse.build(exception.getInternalStatus(), exception.getMessage());
		return ResponseEntity.internalServerError().body(errorResponse);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex,
															 Object body,
															 @NonNull HttpHeaders headers,
															 @NonNull HttpStatusCode statusCode,
															 @NonNull WebRequest request) {
		if (ex instanceof HttpMessageNotReadableException) {
			ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(),
					ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}
}

