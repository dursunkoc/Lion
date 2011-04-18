/**
 *
 */
package com.aric.ruleengine.optimizer;

import java.util.Collections;
import java.util.List;

import com.aric.ruleengine.IRule;

/**
 * @author Dursun KOC
 * 
 */
public class BestBenefitOptimizer implements Optimizer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.Arbitrator#arbitrate
	 * (java.util.List, java.lang.Integer)
	 */
	@Override
	public List<IRule> optimize(List<IRule> rules) {
		Collections.sort(rules, new RuleBenefitComparator());
		return rules;
	}

}
