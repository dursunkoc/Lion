/**
 *
 */
package com.aric.ruleengine.statement;

import java.util.Comparator;

/**
 * @author Dursun KOC
 *
 */
public class StatementComplexityComparator implements Comparator<Statement> {

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Statement o1, Statement o2) {
		int i = o1.getComplexityLevel();
		int j = o2.getComplexityLevel();
		if (i == j) {
			return 0;
		}
		return (i - j) / (Math.abs(i - j));
	}

}
