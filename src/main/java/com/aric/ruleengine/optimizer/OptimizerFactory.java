/**
 *
 */
package com.aric.ruleengine.optimizer;

/**
 * @author Dursun KOC
 *
 */
public class OptimizerFactory {
	private static BestBenefitOptimizer bestBenefitOptimizer = new BestBenefitOptimizer();

	public static Optimizer createOptimizer(Integer optimizerId) {
		// TODO implement me!
		return bestBenefitOptimizer;
	}

}
