/**
 *
 */
package com.aric.ruleengine;

import java.util.LinkedList;
import java.util.List;

import com.aric.esb.environment.Environment;
import com.aric.ruleengine.repository.RuleRepository;
import com.aric.ruleengine.statement.StatementReason;

/**
 * @author Dursun KOC
 * 
 */
public class RuleSniffer {
	private Environment environment;
	private RuleTree sniffTree;
	private RuleTree cursorSniffTree;
	private boolean isInverseRule;
	private RuleRepository persistenceBridge;

	public RuleSniffer(Integer maxInferable,
			RuleRepository persistenceBridge, Environment environment,
			boolean isInverseRule) {
		this.persistenceBridge = persistenceBridge;
		this.environment = environment;
		this.isInverseRule = isInverseRule;
		this.sniffTree = new RuleTree(maxInferable);
		this.cursorSniffTree = this.sniffTree;
	}

	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return this.environment;
	}

	/**
	 * @return the isInverseRule
	 */
	public boolean isInverseRule() {
		return this.isInverseRule;
	}

	/**
	 * @param rule
	 */
	public void sniff(IRule rule) {
		this.addToSniffBagAsPositive(rule);
		List<IRule> children = persistenceBridge.readChildren(rule);
		for (IRule child : children) {
			child.hostSniffer(this);
		}
	}

	/**
	 * @param ruleContext
	 * @param reason
	 */
	private void addToSniffBagAsPositive(IRule rule) {
		if (rule.isInferable()) {
			this.cursorSniffTree.addRule(new PositiveRule(rule));
		}
	}

	/**
	 * @param rule
	 * @param reason
	 */
	public void sniff(IRule rule, StatementReason reason) {
		if (isInverseRule) {
			List<IRule> rules = new LinkedList<IRule>();
			rules.add(rule);
			List<IRule> children = persistenceBridge.readChildren(rule);
			for (IRule child : children) {
				rules.add(child);
			}
			addAllToSniffBagAsNegative(rules, reason);
		}
	}

	/**
	 * @param ruleContexts
	 * @param reason
	 */
	private void addAllToSniffBagAsNegative(List<IRule> rules,
			StatementReason reason) {
		for (IRule rule : rules) {
			if (rule.isInferable()) {
				this.cursorSniffTree.addRule(new NegativeRule(rule, reason));
			}
		}
	}

	/**
	 * @return the sniffTree
	 */
	public RuleTree getSniffTree() {
		return this.sniffTree;
	}

	/**
	 *
	 */
	public void cursorDown(Integer maxInferable) {
		RuleTree tempChild = new RuleTree(maxInferable);
		this.cursorSniffTree.addChild(tempChild);
		this.cursorSniffTree = tempChild;
	}

	/**
	 *
	 */
	public void cursorUp() {
		if (!cursorSniffTree.isRoot()) {
			this.cursorSniffTree = cursorSniffTree.getParent();
		}
	}
}
