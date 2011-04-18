package com.aric.ruleengine.operators;

public class NotEqual extends UnaryOperator {
	private Equal equal;

	public NotEqual() {
		equal = new Equal();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.ruler.operators.UnaryOperator#compareInternal(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	protected boolean compareInternal(Object valueLeft, Object valueRight) {
		return !equal.compare(valueLeft, valueRight);
	}
}
