/**
 *
 */
package com.aric.ruleengine;

import java.util.Map;

import com.aric.ruleengine.repository.RuleRepository;
import com.aric.ruleengine.statement.StatementReason;

/**
 * @author Dursun KOC
 * 
 */
public class NegativeRule implements IRule {
	private IRule rule;
	private StatementReason reason;

	/**
	 * @param rule
	 * @param reason
	 */
	public NegativeRule(IRule rule, StatementReason reason) {
		this.rule = rule;
		this.reason = reason;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.turkcelltech.comet.flow.engine.ruleengine.v2.IRule#getBenefit()
	 */
	@Override
	public Long getBenefit() {
		return rule.getBenefit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.turkcelltech.comet.flow.engine.ruleengine.v2.IRule#getReason()
	 */
	@Override
	public StatementReason getReason() {
		return this.reason;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.turkcelltech.comet.flow.engine.ruleengine.v2.IRule#isInferable()
	 */
	@Override
	public boolean isInferable() {
		return this.rule.isInferable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.turkcelltech.comet.flow.engine.ruleengine.v2.IRule#isNegative()
	 */
	@Override
	public boolean isNegative() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.IRule#retreiveContextBody
	 * (com.turkcelltech.comet.flow.engine.ruleengine.v2.rulereader.RuleReader)
	 */
	@Override
	public Map<String, Object> retreiveContextBody(RuleRepository bridge) {
		return this.rule.retreiveContextBody(bridge);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.IRule#getIdentifier()
	 */
	@Override
	public Long getIdentifier() {
		return this.rule.getIdentifier();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.IRule#hostSniffer(com
	 * .turkcelltech.comet.flow.engine.ruleengine.v2.RuleSniffer)
	 */
	@Override
	public void hostSniffer(RuleSniffer ruleSniffer) {
		throw new UnsupportedOperationException(
				"Negative rules cannot host a sniffer!");
	}

}
