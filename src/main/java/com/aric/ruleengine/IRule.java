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
public interface IRule {
	/**
	 * @return
	 */
	public boolean isNegative();

	/**
	 * @return
	 */
	public boolean isInferable();

	/**
	 * @return
	 */
	public StatementReason getReason();

	/**
	 * @return
	 */
	public Long getBenefit();

	/**
	 * @param reader
	 * @return
	 */
	public Map<String, Object> retreiveContextBody(RuleRepository reader);

	/**
	 * @return
	 */
	public Long getIdentifier();

	/**
	 * @param ruleSniffer
	 */
	public void hostSniffer(RuleSniffer ruleSniffer);
}
