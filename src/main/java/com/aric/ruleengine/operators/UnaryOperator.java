/**
 *
 */
package com.aric.ruleengine.operators;

import com.aric.ruleengine.exceptions.UnsupportedOperatorException;

/**
 * @author Dursun KOC
 * 
 */
public abstract class UnaryOperator implements IOperator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.operators.IOperator#compare(com.turkcelltech
	 * .comet.flow.engine.components.ReturnValue,
	 * com.turkcelltech.comet.flow.engine.components.ReturnValue)
	 */
	@Override
	public boolean compare(Object valueLeft, Object valueRight) {
		if (OperatorUtils.isEitherNull(valueLeft, valueRight)) {
			return false;
		}
		return this.compareInternal(valueLeft, valueRight);
	}

	/**
	 * @param valueLeft
	 * @param valueRight
	 * @return
	 */
	protected abstract boolean compareInternal(Object valueLeft, Object valueRight);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.operators.IOperator#compare(com.turkcelltech
	 * .comet.flow.engine.components.ReturnValue,
	 * com.turkcelltech.comet.flow.engine.components.ReturnValue,
	 * com.turkcelltech.comet.flow.engine.components.ReturnValue)
	 */
	@Override
	public boolean compare(Object valueLeft, Object valueRight,
			Object centerValue) {
		throw new UnsupportedOperatorException(
				"Two value Comparesion is not supported!");
	}

}
