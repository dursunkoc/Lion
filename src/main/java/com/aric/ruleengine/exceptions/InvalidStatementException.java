/**
 * 
 */
package com.aric.ruleengine.exceptions;

/**
 * @author TCDUKOC
 * 
 */
public class InvalidStatementException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6298040033878500824L;

	/**
	 * @param message
	 */
	public InvalidStatementException(String message) {
		super(message);
	}

}
