/**
 *
 */
package com.aric.ruleengine;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.aric.ruleengine.optimizer.Optimizer;
import com.aric.ruleengine.optimizer.OptimizerFactory;

/**
 * @author Dursun KOC
 * 
 */
public class RuleTree {
	private Integer maxInferable;
	private RuleTree parent;
	private List<RuleTree> children;
	private List<IRule> rules;
	private Integer optimizerId;

	/**
	 * @param maxInferable
	 */
	public RuleTree(Integer maxInferable) {
		this(maxInferable, null);
	}

	/**
	 * @param maxInferable
	 * @param parent
	 */
	public RuleTree(Integer maxInferable, RuleTree parent) {
		this.maxInferable = maxInferable;
		this.rules = new LinkedList<IRule>();
		children = new LinkedList<RuleTree>();
		this.parent = parent;
	}

	/**
	 * @return
	 */
	public boolean isRoot() {
		return this.parent == null;
	}

	/**
	 * @return
	 */
	public RuleTree getParent() {
		return this.parent;
	}

	/**
	 * @param child
	 */
	public void addRule(IRule rule) {
		this.rules.add(rule);
	}

	/**
	 * @param child
	 */
	public void addChild(RuleTree child) {
		this.children.add(child);
		child.parent = this;
	}

	/**
	 * @param child
	 */
	public void addAllRule(List<IRule> rules) {
		this.rules.addAll(rules);
	}

	/**
	 * @return the whole sucessful rules through the path.
	 */
	public List<IRule> gatherRulesWithoutPrune() {
		for (RuleTree child : children) {
			this.rules.addAll(child.gatherRulesWithoutPrune());
		}
		return this.rules;
	}

	/**
	 * @return a part of the sucessful rules through the path by applying
	 *         optimization.
	 */
	public List<IRule> gatherRulesWithPrune() {
		for (RuleTree child : children) {
			this.rules.addAll(child.gatherRulesWithPrune());
		}
		return gatherRulesWithPruneInternal();
	}

	/**
	 * @return
	 */
	private List<IRule> gatherRulesWithPruneInternal() {
		Optimizer optimizer = OptimizerFactory
				.createOptimizer(this.optimizerId);
		List<IRule> optimizedRules = optimizer.optimize(rules);
		int pruneLimit = this.decidePruneLimit(optimizedRules);
		List<IRule> subList = optimizedRules.subList(0, pruneLimit);
		return subList;
	}

	/**
	 * @param optimizedRules
	 * @return
	 */
	private int decidePruneLimit(List<IRule> optimizedRules) {
		int pruneLimit = NumberUtils.min(new int[] { maxInferable,
				optimizedRules.size() });
		return pruneLimit;
	}

	/**
	 * @return the children
	 */
	public List<RuleTree> getChildren() {
		return this.children;
	}

	public String printOutRuleTree(int l) {
		StringBuffer b = new StringBuffer();
		String lI = "";
		for (int i = 0; i < l; i++) {
			lI += " ";
		}
		b.append(lI + "AddedRules: ");
		b.append("[ ");
		for (IRule r : rules) {
			b.append("R-" + r.getIdentifier() + "(b:" + r.getBenefit() + ")");
			b.append(" ");
		}
		b.append("]");
		b.append("->" + maxInferable);
		b.append("{");
		b.append("\n");
		for (RuleTree rt : children) {
			b.append(rt.printOutRuleTree(l + 1));
			b.append("\n");
		}
		b.append(lI + "}");
		return b.toString();
	}

}
