package com.aric.ruleengine.operators;

public class NotLike extends UnaryOperator {
	private Like like;

	public NotLike() {
		like = new Like();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.ruler.operators.UnaryOperator#compareInternal(java.lang.Object,
	 * java.lang.Object)
	 */
	protected boolean compareInternal(Object valueLeft, Object valueRight) {
		return !like.compare(valueLeft, valueRight);
	}

}
