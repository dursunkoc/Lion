package com.aric.ruleengine.operators;

import com.aric.ruleengine.exceptions.InvalidTypeForOperatorException;

/**
 * @author TCDUKOC
 * 
 */
public class And extends UnaryOperator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.operators.IOperator#compare(com.turkcelltech
	 * .comet.flow.engine.basicflow.domain .ReturnValue,
	 * com.turkcelltech.comet.flow.engine.basicflow.domain.ReturnValue)
	 */
	@Override
	protected boolean compareInternal(Object valueLeft, Object valueRight) {

		if (OperatorUtils.isBothInstanceOf(valueLeft, valueRight, Boolean.class)) {
			return (Boolean) valueLeft && (Boolean) valueRight;
		}
		throw new InvalidTypeForOperatorException(
				"Both of the operand should be Boolean!(valueLeft.class:'"
						+ valueLeft.getClass() + "'; valueRight.class:'"
						+ valueRight.getClass() + "')");
	}

}