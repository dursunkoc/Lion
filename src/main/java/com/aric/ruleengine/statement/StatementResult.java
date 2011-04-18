/**
 *
 */
package com.aric.ruleengine.statement;

import com.aric.ruleengine.exceptions.InvalidStatementResultException;

/**
 * @author Dursun KOC
 * 
 */
public class StatementResult {
	private Object result;
	private StatementReason reason;

	/**
	 * @param result
	 * @param reason
	 */
	public StatementResult(Object result, StatementReason reason) {
		if (result == null) {
			throw new RuntimeException(
					"A StatementResult cannot be built with a null result!");
		}
		this.result = result;
		this.reason = reason;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return this.result;
	}

	/**
	 * @return the reason
	 */
	public StatementReason getReason() {
		return this.reason;
	}

	/**
	 * @return
	 */
	public boolean isSuccessful() {
		if (result instanceof Boolean) {
			Boolean bool = (Boolean) result;
			return bool;
		}
		throw new InvalidStatementResultException(
				"The type of statement-result should be Boolean!");
	}

	/**
	 * @param result
	 * @return
	 */
	public static StatementResult resultForSimpleStatements(Object result) {
		return new StatementResult(result, null);
	}

	/**
	 * @param result
	 * @param reason
	 * @return
	 */
	public static StatementResult resultForComplexStatements(Object result,
			StatementReason reason) {
		return new StatementResult(result, reason);
	}

}
