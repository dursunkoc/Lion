package com.aric.ruleengine.operators;

/**
 * 
 * @author TCDUKOC
 * 
 */
public class SmallerThan extends UnaryOperator {

	private GreaterThan greaterThan;
	private Equal equal;

	public SmallerThan() {
		greaterThan = new GreaterThan();
		equal = new Equal();
	}

	@Override
	protected boolean compareInternal(Object valueLeft, Object valueRight) {
		return !(greaterThan.compare(valueLeft, valueRight) || equal.compare(
				valueLeft, valueRight));
	}

}
