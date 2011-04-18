package com.aric.ruleengine.operators;

public class NotIn extends UnaryOperator {

	private In in;

	public NotIn() {
		in = new In();
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
		return !in.compare(valueLeft, valueRight);
	}
}
