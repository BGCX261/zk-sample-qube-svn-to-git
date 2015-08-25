package it.qube.core;

public class ApplicationError extends Error {

	private static final long serialVersionUID = 1L;


	public ApplicationError(String message) {
		super(message);
	}

	public ApplicationError(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationError(Throwable cause) {
		super(cause);
	}

}
