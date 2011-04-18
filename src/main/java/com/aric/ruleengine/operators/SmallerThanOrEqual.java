/**
 *
 */
package com.aric.ruleengine.operators;

/**
 * @author Dursun KOC
 * 
 */
public class SmallerThanOrEqual extends UnaryOperator {

	private GreaterThan greaterThan;

	public SmallerThanOrEqual() {
		greaterThan = new GreaterThan();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.operators.IOperator#compare(com.turkcelltech
	 * .comet.flow.engine.basicflow.node.components.ReturnValue,
	 * com.turkcelltech.comet.flow.engine.basicflow.node.components.ReturnValue)
	 */
	@Override
	protected boolean compareInternal(Object valueLeft, Object valueRight) {
		return !(greaterThan.compare(valueLeft, valueRight));
	}
}
