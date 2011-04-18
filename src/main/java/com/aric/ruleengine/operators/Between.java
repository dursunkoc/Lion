/**
 *
 */
package com.aric.ruleengine.operators;

/**
 * @author Dursun KOC
 * 
 */
public class Between extends BinaryOperator {
	private SmallerThanOrEqual smallerThanOrEqual;
	private GreaterThanOrEqual greaterThanOrEqual;

	/**
	 *
	 */
	public Between() {
		smallerThanOrEqual = new SmallerThanOrEqual();
		greaterThanOrEqual = new GreaterThanOrEqual();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.operators.BinaryOperator#compare(com.turkcelltech
	 * .comet.flow.engine.components.ReturnValue,
	 * com.turkcelltech.comet.flow.engine.components.ReturnValue,
	 * com.turkcelltech.comet.flow.engine.components.ReturnValue)
	 */
	@Override
	public boolean compare(Object valueLeft, Object valueRight,
			Object centerValue) {

		boolean comparison = smallerThanOrEqual
				.compare(centerValue, valueRight)
				&& greaterThanOrEqual.compare(centerValue, valueLeft);
		return comparison;
	}

}
