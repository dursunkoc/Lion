package com.aric.ruleengine.operators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import com.aric.ruleengine.exceptions.NonIterableObjectException;

public abstract class OperatorUtils {

	/**
	 * @param dateValue
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDate(String dateValue, String dateFormat)
			throws ParseException {
		if (dateFormat.length() - dateValue.length() >= 5) {
			dateValue = new StringBuffer(dateValue).append(" 00:00:00")
					.toString();
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		Date resultDate = simpleDateFormat.parse(dateValue);
		return resultDate;
	}

	/**
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateToString(Date date, String dateFormat) {
		return DateFormatUtils.format(date, dateFormat);
	}

	/**
	 * @param valueLeft
	 * @param valueRight
	 * @return
	 */
	public static boolean isEitherNull(Object valueLeft, Object valueRight) {
		return (valueLeft == null || valueRight == null);
	}

	/**
	 * @param valueLeft
	 * @param valueRight
	 * @param cls
	 * @return
	 */
	public static boolean isBothInstanceOf(Object valueLeft, Object valueRight,
			Class<?> cls) {
		return cls.isInstance(valueLeft) && cls.isInstance(valueRight);
	}

	/**
	 * @param object
	 * @return
	 * @throws NonIterableObjectException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Iterable<Object> toIterable(Object object)
			throws NonIterableObjectException {
		if (object instanceof Iterable) {
			Iterable iterable = (Iterable) object;
			return iterable;
		}
		if (object instanceof Object[]) {
			Object array[] = (Object[]) object;
			return arrayToIterable(array);
		}

		throw new NonIterableObjectException("'" + object.getClass()
				+ "' cannot be converted into iterable!");
	}

	/**
	 * @param array
	 * @return
	 */
	private static Iterable<Object> arrayToIterable(Object[] array) {
		List<Object> list = Arrays.asList(array);
		return (Iterable<Object>) list;
	}

	/**
	 * @param valueLeft
	 * @param valueRight
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int makeComparableAndCompare(Object valueLeft,
			Object valueRight) {
		Comparable lhsValue = (Comparable) valueLeft;
		Comparable rhsValue = (Comparable) valueRight;
		return lhsValue.compareTo(rhsValue);
	}

}
