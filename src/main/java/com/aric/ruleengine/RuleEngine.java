/**
 *
 */
package com.aric.ruleengine;

import java.util.LinkedList;
import java.util.List;

import com.aric.esb.environment.Environment;
import com.aric.ruleengine.repository.RuleRepository;

/**
 * @author Dursun KOC
 * 
 */
public class RuleEngine {
	private RuleRepository persitenceBridge;

	/**
	 * @param ruleReader
	 */
	public RuleEngine(RuleRepository bridge) {
		this.persitenceBridge = bridge;
	}

	/**
	 * @param environment
	 * @param inverseRule
	 * @return
	 */
	public List<IRule> execute(String packageName, Environment environment,
			Integer maxInferable, boolean inverseRule) {
		IRule rule = persitenceBridge.readRootRule(packageName);
		List<IRule> inferedRules = new LinkedList<IRule>();
		if (rule != null) {
			RuleSniffer ruleSniffer = new RuleSniffer(maxInferable,
					persitenceBridge, environment, inverseRule);
			rule.hostSniffer(ruleSniffer);
			RuleTree sniffTree = ruleSniffer.getSniffTree();
			String printOutRuleTree = sniffTree.printOutRuleTree(0);
			if (!inverseRule) {
				inferedRules = sniffTree.gatherRulesWithPrune();
			} else {
				inferedRules = sniffTree.gatherRulesWithoutPrune();
			}
			System.out.println("Inverse Rules:" + inverseRule);
			System.out.println(printOutRuleTree);
		}

		return inferedRules;
	}
}
