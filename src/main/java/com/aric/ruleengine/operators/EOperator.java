/**
 *
 */
package com.aric.ruleengine.operators;

/**
 * @author Dursun KOC
 * 
 */
public enum EOperator {
	EQUAL("equal", "==", new Equal(), false), 
	NOT_EQUAL("notEqual", "!=", new NotEqual(), false), 
	IN("in", "in", new In(), false), 
	NOT_IN("notIn", "!in", new NotIn(), false), 
	LIKE("like", "like", new Like(), false), 
	NOT_LIKE("notLike", "notLike", new NotLike(), false), 
	GREATER("GreaterThan", ">", new GreaterThan(), false), 
	GREATER_OR_EQUAL("GreaterThanOrEqual", ">=", new GreaterThanOrEqual(), false), 
	SMALLER("SmallerThan", "<", new SmallerThan(), false), 
	SMALLER_OR_EQUAL("SmallerThanOrEqual", "<=", new SmallerThanOrEqual(), false), 
	AND("And", "&&", new And(), true), 
	OR("Or", "||", new Or(), true), 
	CONTAINS("Contains", "/\\", new Contains(), false), 
	NOT_CONTAINS("notContains", "\\/", new NotContains(), false), 
	BETWEEN("between", "btwin", new Between(), false);

	private final String operatorPrimoName;
	private final String operatorSecundoName;
	private final IOperator iOperator;
	private final boolean isBoolOperator;

	EOperator(String operatorPrimoName, String operatorSecundoName,
			IOperator iOperator, boolean isBoolOperator) {
		this.operatorPrimoName = operatorPrimoName;
		this.operatorSecundoName = operatorSecundoName;
		this.iOperator = iOperator;
		this.isBoolOperator = isBoolOperator;
	}

	public boolean isMe(String operator) {
		return operator.equalsIgnoreCase(this.operatorPrimoName)
				|| operator.equalsIgnoreCase(this.operatorSecundoName);
	}

	public boolean isBoolOperator() {
		return this.isBoolOperator;
	}

	/**
	 * @return the iOperator
	 */
	public IOperator getiOperator() {
		return this.iOperator;
	}

	/**
	 * @param valueLeft
	 * @param valueRight
	 * @return
	 */
	public boolean compare(Object valueLeft, Object valueRight) {
		return this.iOperator.compare(valueLeft, valueRight);
	}

	/**
	 * @param valueLeft
	 * @param valueRight
	 * @param centerValue
	 * @return
	 */
	public boolean compare(Object valueLeft, Object valueRight,
			Object centerValue) {
		return this.iOperator.compare(valueLeft, valueRight, centerValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.operatorPrimoName;
	}

	public String getStrVal() {
		return this.operatorPrimoName;
	}

}
