package de.element.insurance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Param invalid.")
public class InvalidParamException extends Exception {

	private static final long serialVersionUID = 6991349172779844706L;

	public InvalidParamException(String message) {
		super(message);
	}
}
