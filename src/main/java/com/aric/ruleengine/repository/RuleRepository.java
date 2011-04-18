/**
 *
 */
package com.aric.ruleengine.repository;

import java.util.List;
import java.util.Map;

import com.aric.ruleengine.IRule;

/**
 * @author Dursun KOC
 * 
 */
public interface RuleRepository {
	/**
	 * @param ruleNamespace
	 * @return
	 */
	public IRule readRootRule(String ruleNamespace);

	/**
	 * @param parent
	 * @return
	 */
	public List<IRule> readChildren(IRule parent);

	/**
	 * @param identifier
	 * @return
	 */
	public Map<String, Object> readContext(Long identifier);

}
