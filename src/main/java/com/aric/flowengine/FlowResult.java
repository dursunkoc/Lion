/**
 * 
 */
package com.aric.flowengine;

import java.util.Map;

import com.aric.esb.environment.Variable;

/**
 * Represents the execution result of a flow.
 * 
 * @author Dursun KOC
 */
public class FlowResult {
	private boolean isSuccessfull;
	private Map<String, Variable> resultMap;
	private Exception exception;

	/**
	 * Full Constructor.
	 * 
	 * @param isSuccessfull
	 * @param resultMap
	 * @param exception
	 */
	private FlowResult(boolean isSuccessfull, Map<String, Variable> resultMap,
			Exception exception) {
		this.isSuccessfull = isSuccessfull;
		this.resultMap = resultMap;
		this.exception = exception;
	}

	/**
	 * When result is successful.
	 * 
	 * @param resultMap
	 */
	public FlowResult(Map<String, Variable> resultMap) {
		this(true, resultMap, null);
	}

	/**
	 * When result is unsuccessful.
	 * 
	 * @param resultMap
	 * @param exception
	 */
	public FlowResult(Map<String, Variable> resultMap, Exception exception) {
		this(false, resultMap, exception);
	}

	/**
	 * Whether or not the result is successful.
	 * @return - issuccessfull
	 */
	public boolean isSuccessfull() {
		return isSuccessfull;
	}

	/**
	 * @return
	 */
	public Map<String, Variable> getResultMap() {
		return resultMap;
	}

	/**
	 * Reason why flow result is unsuccessful
	 * 
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}

}
