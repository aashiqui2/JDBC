package com.jdbc.exception;

/*
 * Exception (Checked Exception, parent class)
 * Requires you to declare it in the method signature (throws Exception).
 * Forces the caller to handle it using try-catch.
 * Usually used for low-level issues (like I/O, database errors, file handling).
 */

public class DuplicateOwnerException extends Exception {
	private static final long serialVersionUID = 1L;

	public DuplicateOwnerException(String message) {
		super(message);
	}
}
