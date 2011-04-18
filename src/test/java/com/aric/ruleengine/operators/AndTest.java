package com.aric.ruleengine.operators;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aric.ruleengine.exceptions.InvalidTypeForOperatorException;

public class AndTest {
	private static IOperator op;

	@BeforeClass
	public static void beforeClass() {
		op = EOperator.AND.getiOperator();
	}

	@Test(expected = InvalidTypeForOperatorException.class)
	public void testCompareShouldRaiseInvalidTypeForOperatorExceptionWhenNonBooleanObjectsProvided() {
		op.compare("ali", "veli");
	}

	@Test()
	public void testCompareShouldReturnTrueWhenTrueAndTrue() {
		Assert.assertTrue(op.compare(true, true));
	}

	@Test()
	public void testCompareShouldReturnFalseWhenTrueAndFalse() {
		Assert.assertFalse(op.compare(true, false));
	}

	@Test()
	public void testCompareShouldReturnFalseWhenFalseAndTrue() {
		Assert.assertFalse(op.compare(false, true));
	}

	@Test()
	public void testCompareShouldReturnFalseWhenFalseAndFalse() {
		Assert.assertFalse(op.compare(false, false));
	}

}
