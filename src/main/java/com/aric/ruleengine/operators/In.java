package com.aric.ruleengine.operators;

import com.aric.ruleengine.exceptions.GenericOperatorException;

public class In extends UnaryOperator {
	private Equal equal;

	public In() {
		equal = new Equal();
	}

	@Override
	protected boolean compareInternal(Object valueLeft, Object valueRight) {
		// TODO implement me!!
		try {
			Iterable<Object> iterable = OperatorUtils.toIterable(valueRight);
			for (Object rightItem : iterable) {
				boolean catched = this.equal.compare(valueLeft, rightItem);
				if (catched) {
					return true;
				}
			}
			return false;
		} catch (Throwable e) {
			throw new GenericOperatorException(
					"In Operator threw following exception!", e);
		}
	}
}
