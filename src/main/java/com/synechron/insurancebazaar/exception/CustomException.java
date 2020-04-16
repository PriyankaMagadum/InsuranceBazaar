package com.synechron.insurancebazaar.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.synechron.insurancebazaar.config.Response;

@ControllerAdvice
@RestController
public class CustomException extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return getResponseEntity(ex.getBindingResult().getFieldError().getDefaultMessage());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleConstaintViolationExcepiton(DataIntegrityViolationException ex,
			WebRequest request) {

		String message = ex.getMostSpecificCause().getMessage();
		String array[] = message.split(" ");
		message = "";
		for (String s : array) {
			if (s.equals("for")) {
				break;
			} else {
				message = message + " " + s;
			}
		}

		return getResponseEntity(message);
	}

	private ResponseEntity<Object> getResponseEntity(String message) {
		return new ResponseEntity<Object>(new Response(message, null), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExcepiton(Exception ex, WebRequest request) {

		System.out.println("ex message=" + ex.getMessage());

		return getResponseEntity(ex.getMessage());
	}

}
