package com.aric.ruleengine.operators;

/**
 * 
 * @author TCDUKOC
 * 
 */
public class Contains extends UnaryOperator {
	private In in;

	public Contains() {
		in = new In();
	}

	@Override
	protected boolean compareInternal(Object valueLeft, Object valueRight) {
		return in.compareInternal(valueRight, valueLeft);
	}

}
