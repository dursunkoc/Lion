package com.aric.ruleengine.operators;

/**
 * 
 * @author TCDUKOC
 * 
 */
public class NotContains extends UnaryOperator {
	private Contains contains;

	public NotContains() {
		contains = new Contains();
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
		return !contains.compare(valueLeft, valueRight);
	}

}
