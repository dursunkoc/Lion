package com.aric.ruleengine.operators;

import com.aric.ruleengine.exceptions.InvalidTypeForOperatorException;

public class GreaterThan extends UnaryOperator {

	@Override
	protected boolean compareInternal(Object valueLeft, Object valueRight) {
		if (OperatorUtils.isBothInstanceOf(valueLeft, valueRight,
				Comparable.class)) {
			int compareResult = OperatorUtils.makeComparableAndCompare(
					valueLeft, valueRight);
			return compareResult == 1;
		}
		throw new InvalidTypeForOperatorException(
				"Both of the operands should be Instance of Compareable");
	}

}
