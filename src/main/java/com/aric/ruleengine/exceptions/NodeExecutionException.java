/**
 * 
 */
package com.aric.ruleengine.exceptions;

/**
 * @author Dursun KOC
 * 
 */
public class NodeExecutionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7497307579066430725L;

	/**
	 * 
	 */
	public NodeExecutionException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NodeExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NodeExecutionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NodeExecutionException(Throwable cause) {
		super(cause);
	}

}
