/**
 *
 */
package com.aric.ruleengine.operators;

/**
 * @author Dursun KOC
 * 
 */
public class GreaterThanOrEqual extends UnaryOperator {
	private SmallerThan smallerThan;

	public GreaterThanOrEqual() {
		smallerThan = new SmallerThan();
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
		return !(smallerThan.compare(valueLeft, valueRight));
	}

}
