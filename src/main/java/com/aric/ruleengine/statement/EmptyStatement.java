/**
 *
 */
package com.aric.ruleengine.statement;

import com.aric.esb.environment.Environment;

/**
 * @author Dursun KOC
 * 
 */
public class EmptyStatement implements Statement {
	private static final long serialVersionUID = -2851420364734048608L;
	private static final EmptyStatement instance = new EmptyStatement();

	/**
	 *
	 */
	private EmptyStatement() {
	}

	public static EmptyStatement getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.turkcelltech.comet.flow.util.statement.v2.Statement#execute(com.
	 * turkcelltech.comet.flow.engine.components.Environment, boolean)
	 */
	@Override
	public StatementResult execute(Environment environment, boolean isDebug) {
		return this.execute(environment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.ruleengine.statement.Statement#execute(com.aric.esb.environment
	 * .Environment)
	 */
	@Override
	public StatementResult execute(Environment environment) {
		Object result = new Boolean(true);
		return StatementResult.resultForSimpleStatements(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.turkcelltech.comet.flow.util.statement.v2.Statement#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.statement.Statement#statement2String()
	 */
	@Override
	public String statement2String() {
		return Statement.EMPTY_STATEMENT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.statement.Statement#findMinimumSatisfaction()
	 */
	@Override
	public Statement[] findMinimumSatisfaction() {
		return new Statement[] {};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.statement.Statement#getComplexityLevel()
	 */
	@Override
	public int getComplexityLevel() {
		return COMPLEXITY_BASE;
	}

}
