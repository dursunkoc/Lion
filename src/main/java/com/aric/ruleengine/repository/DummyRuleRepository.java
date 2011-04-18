/**
 *
 */
package com.aric.ruleengine.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aric.ruleengine.IRule;
import com.aric.ruleengine.NeutralRule;
import com.aric.ruleengine.optimizer.BestBenefitOptimizer;
import com.aric.ruleengine.optimizer.Optimizer;
import com.aric.ruleengine.statement.Statement;

/**
 * @author Dursun KOC
 * 
 */
public class DummyRuleRepository implements RuleRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.RuleReader#readRootRule
	 * ()
	 */
	@Override
	public IRule readRootRule(String ruleNamespace) {
		Statement statement = null;
		Long identifier = 100L;
		Optimizer optimizer = new BestBenefitOptimizer();
		Integer maxInferable = null;
		Long benefit = null;
		boolean isInferable = false;
		IRule root = new NeutralRule(statement, maxInferable, benefit,
				isInferable, identifier, optimizer);
		return root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.RuleReader#readChildren
	 * (com.turkcelltech.comet.flow.engine.ruleengine.v2.NeutralRule)
	 */
	@Override
	public List<IRule> readChildren(IRule parent) {
		if (parent.getIdentifier() == 100L) {
			return firstLevel(parent.getIdentifier());
		}
		if (parent.getIdentifier() < 2000L) {
			return secondLevel(parent.getIdentifier());
		}
		return new LinkedList<IRule>();
	}

	/**
	 * @param parentIdentifier
	 * @return
	 */
	private List<IRule> firstLevel(Long parentIdentifier) {
		List<IRule> rules = new LinkedList<IRule>();
		Statement statement = null;
		Integer maxInferable = null;
		Long benefit = null;
		boolean isInferable = false;
		Long identifier = 1000L;
		Optimizer optimizer = new BestBenefitOptimizer();
		IRule rule01 = new NeutralRule(statement, maxInferable, benefit,
				isInferable, identifier, optimizer);
		IRule rule02 = new NeutralRule(statement, maxInferable, benefit,
				isInferable, identifier + 3, optimizer);
		IRule rule03 = new NeutralRule(statement, maxInferable, benefit,
				isInferable, identifier + 6, optimizer);
		rules.add(rule01);
		rules.add(rule02);
		rules.add(rule03);
		return rules;
	}

	/**
	 * @param long1
	 * @return
	 */
	private List<IRule> secondLevel(Long parentIdentifier) {
		List<IRule> rules = new LinkedList<IRule>();
		Statement statement = null;
		Integer maxInferable = 10;
		Long identifier = 1000L;
		Long benefit = 100L;
		boolean isInferable = true;
		Optimizer optimizer = new BestBenefitOptimizer();
		IRule rule01 = new NeutralRule(statement, maxInferable, benefit,
				isInferable, parentIdentifier + identifier, optimizer);
		// IRule rule02 = new NeutralRule(StatementUtils
		// .string2Statement("( [ 1¨ || >1> ] equal [ 2¨ || >1> ] )"),
		// maxInferable, benefit, isInferable, parentIdentifier
		// + identifier + 1, optimizer);
		IRule rule02 = new NeutralRule(statement, maxInferable, benefit,
				isInferable, parentIdentifier + identifier + 1, optimizer);
		IRule rule03 = new NeutralRule(statement, maxInferable, benefit,
				isInferable, parentIdentifier + identifier + 2, optimizer);
		rules.add(rule01);
		rules.add(rule02);
		rules.add(rule03);
		return rules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.rulereader.RuleReader
	 * #readContext(java.lang.Long)
	 */
	@Override
	public Map<String, Object> readContext(Long identifier) {
		return null;
	}

}
