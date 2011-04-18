/**
 *
 */
package com.aric.ruleengine.exceptions;

/**
 * @author Dursun KOC
 * 
 */
public class InvalidRuleConstruction extends RuntimeException {

	private static final long serialVersionUID = -7009362783803824496L;

	/**
	 * @param message
	 */
	public InvalidRuleConstruction(String message) {
		super(message);
	}

}
