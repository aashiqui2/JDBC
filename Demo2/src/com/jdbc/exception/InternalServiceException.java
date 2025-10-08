package com.jdbc.exception;

/*
 * RuntimeException (Unchecked Exception)
 * Does not require throws in the method signature
 */

public class InternalServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InternalServiceException(String message) {
		super(message);
	}
}
