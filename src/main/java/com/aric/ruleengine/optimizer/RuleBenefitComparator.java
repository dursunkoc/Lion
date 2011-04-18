/**
 *
 */
package com.aric.ruleengine.optimizer;

import java.util.Comparator;

import com.aric.ruleengine.IRule;

/**
 * @author Dursun KOC
 *
 */
public class RuleBenefitComparator implements Comparator<IRule> {

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(IRule r1, IRule r2) {
		return -r1.getBenefit().compareTo(r2.getBenefit());
	}

}
