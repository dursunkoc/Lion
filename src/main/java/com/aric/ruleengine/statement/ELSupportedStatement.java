/**
 * 
 */
package com.aric.ruleengine.statement;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.aric.esb.environment.Environment;

/**
 * @author Dursun KOC
 * 
 */
public class ELSupportedStatement implements Statement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8560818128471936855L;
	private ExpressionParser parser = new SpelExpressionParser();
	private Expression expression;

	/**
	 * @param source
	 */
	public ELSupportedStatement(String source) {
		this.expression = parser.parseExpression(source);
	}

	@Override
	public StatementResult execute(Environment environment, boolean isDebug) {
		return execute(environment);
	}

	@Override
	public StatementResult execute(Environment environment) {
		EvaluationContext context = new StandardEvaluationContext(environment);
		Object value = expression.getValue(context);
		return StatementResult.resultForSimpleStatements(value);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public String statement2String() {
		return expression.getExpressionString();
	}

	@Override
	public Statement[] findMinimumSatisfaction() {
		return null;
	}

	@Override
	public int getComplexityLevel() {
		return 0;
	}

}
