package com.liferay.practice.exercise.registration.exception;

import java.util.List;

public class RegistrationException extends Exception {
	public RegistrationException(List<String> errors) {
		super(String.join(",", errors));
		_errors = errors;
	}

	public List<String> getErrors() {

		return _errors;
	}

	private List<String> _errors;

	public RegistrationException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegistrationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RegistrationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RegistrationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
