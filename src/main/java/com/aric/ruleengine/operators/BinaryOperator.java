/**
 *
 */
package com.aric.ruleengine.operators;

import com.aric.ruleengine.exceptions.UnsupportedOperatorException;

/**
 * @author Dursun KOC
 * 
 */
public abstract class BinaryOperator implements IOperator {

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
		throw new UnsupportedOperatorException(
				"One value Comparesion is not supported!");
	}

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
	public abstract boolean compare(Object valueLeft, Object valueRight,
			Object centerValue);

}
