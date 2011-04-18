/**
 * 
 */
package com.aric.ruleengine.exceptions;

import java.io.IOException;

/**
 * @author Dursun KOC
 * 
 */
public class InvalidPersistenceFileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1595006049734688161L;

	/**
	 * @param message
	 */
	public InvalidPersistenceFileException(String message) {
		super(message);
	}

	/**
	 * @param e
	 */
	public InvalidPersistenceFileException(IOException e) {
		super(e);
	}

}
