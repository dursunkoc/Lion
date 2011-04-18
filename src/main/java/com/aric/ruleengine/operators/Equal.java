package com.aric.ruleengine.operators;

public class Equal extends UnaryOperator {
	@Override
	protected boolean compareInternal(Object valueLeft, Object valueRight) {
		if (OperatorUtils.isBothInstanceOf(valueLeft, valueRight,
				Comparable.class)) {
			int compareResult = OperatorUtils.makeComparableAndCompare(
					valueLeft, valueRight);
			return compareResult == 0;
		}
		return valueLeft.equals(valueRight);
	}
}
