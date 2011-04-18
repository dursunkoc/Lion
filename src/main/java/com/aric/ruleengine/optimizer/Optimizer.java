/**
 *
 */
package com.aric.ruleengine.optimizer;

import java.util.List;

import com.aric.ruleengine.IRule;

/**
 * @author Dursun KOC
 * 
 */
public interface Optimizer {

	/**
	 * @param rules
	 * @return
	 */
	public List<IRule> optimize(List<IRule> rules);

}
