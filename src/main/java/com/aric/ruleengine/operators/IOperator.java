package com.aric.ruleengine.operators;

public interface IOperator {
	/**
	 * compare valueLeft using valueRight
	 * 
	 * @param valueLeft
	 * @param valueRight
	 * @return
	 */
	public boolean compare(Object valueLeft, Object valueRight);

	/**
	 * compare centerValue using valuLeft and valueRight
	 * 
	 * @param valueLeft
	 * @param valueRight
	 * @return
	 */
	public boolean compare(Object valueLeft, Object valueRight,
			Object centerValue);
}
