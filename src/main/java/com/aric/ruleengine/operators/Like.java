package com.aric.ruleengine.operators;

import com.aric.ruleengine.exceptions.InvalidTypeForOperatorException;

public class Like extends UnaryOperator {

	@Override
	protected boolean compareInternal(Object valueLeft, Object valueRight) {
		if (OperatorUtils.isBothInstanceOf(valueLeft, valueRight, String.class)) {
			String localLeftValue = (String) valueLeft;
			String localRightValue = (String) valueRight;
			localRightValue = localRightValue.replace("\\", "\\\\").replace(
					"[", "\\[").replace("^", "\\^").replace("$", "\\$")
					.replace(".", "\\.").replace("|", "\\|")
					.replace("?", "\\?").replace("*", "\\*")
					.replace("+", "\\+").replace("(", "\\(")
					.replace(")", "\\)").replace("_", ".").replace("%", "(.*)");

			return localLeftValue.matches(localRightValue);
		}
		throw new InvalidTypeForOperatorException(
				"Like operator is only suitable for String operands!");
	}
}
