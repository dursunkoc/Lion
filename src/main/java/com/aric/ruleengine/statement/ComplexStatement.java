/**
 *
 */
package com.aric.ruleengine.statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aric.esb.environment.Environment;
import com.aric.ruleengine.exceptions.CorrectionIsNotPossibleException;
import com.aric.ruleengine.operators.EOperator;

/**
 * @author Dursun KOC
 * 
 */
public class ComplexStatement implements Statement {
	private static final long serialVersionUID = -8629178877062765391L;
	private Statement lhsStatement;
	private Statement rhsStatement;
	private Statement centerStatement;
	private EOperator operator;

	/**
	 * @param lhsStatement
	 * @param rhsStatement
	 * @param operator
	 */
	public ComplexStatement(Statement lhsStatement, Statement rhsStatement,
			EOperator operator) {
		this.lhsStatement = lhsStatement;
		this.rhsStatement = rhsStatement;
		this.operator = operator;
	}

	/**
	 * @param lhsStatement
	 * @param centerStateme
	 * @param rhsStatement
	 * @param operator
	 */
	public ComplexStatement(Statement lhsStatement, Statement centerStateme,
			Statement rhsStatement, EOperator operator) {
		this.lhsStatement = lhsStatement;
		this.rhsStatement = rhsStatement;
		this.operator = operator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.turkcelltech.comet.flow.util.statement.v2.Statement#execute(com.
	 * turkcelltech.comet.flow.engine.components.Environment, boolean)
	 */
	@Override
	public StatementResult execute(Environment environment) {
		return simpleExecute(environment);
	}

	/**
	 * @param environment
	 * @return
	 */
	private StatementResult simpleExecute(Environment environment) {
		if (operator.isBoolOperator()) {
			return optimizedBoolOperator(environment);
		}
		StatementResult lhsResult = lhsStatement.execute(environment);
		StatementResult rhsResult = rhsStatement.execute(environment);
		StatementResult retVal = doOperation(environment, lhsResult, rhsResult);
		return retVal;
	}

	/**
	 * @param environment
	 * @param isDebug
	 * @return
	 */
	private StatementResult optimizedBoolOperator(Environment environment) {
		StatementResult lhsResult = lhsStatement.execute(environment);
		Object lhsRetVal = lhsResult.getResult();
		Boolean lhsValue = (Boolean) lhsRetVal;
		if (lhsValue && operator == EOperator.OR) {
			return StatementResult.resultForSimpleStatements(lhsRetVal);
		} else if (!lhsValue && operator == EOperator.AND) {
			return StatementResult.resultForSimpleStatements(lhsRetVal);
		}
		StatementResult rhsResult = rhsStatement.execute(environment);
		StatementResult retVal = doOperation(environment, lhsResult, rhsResult);
		return retVal;
	}

	/**
	 * @param environment
	 * @param isDebug
	 * @param lhsResult
	 * @param rhsResult
	 * @return
	 */
	private StatementResult doOperation(Environment environment,
			StatementResult lhsResult, StatementResult rhsResult) {
		boolean result;
		if (centerStatement == null) {
			result = operator.compare(lhsResult.getResult(), rhsResult
					.getResult());
		} else {
			StatementResult cntrValue = centerStatement.execute(environment);
			result = operator.compare(lhsResult.getResult(), rhsResult
					.getResult(), cntrValue.getResult());
		}

		StatementResult retVal = buildUpResult(lhsResult, rhsResult, result);
		return retVal;
	}

	/**
	 * @param lhsValue
	 * @param rhsValue
	 * @param result
	 * @param isDebug
	 * @return
	 */
	private StatementResult buildUpResult(StatementResult lhsValue,
			StatementResult rhsValue, boolean result) {
		StatementResult retVal = StatementResult.resultForComplexStatements(
				result, StatementReason.EMPTY_REASON);
		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.turkcelltech.comet.flow.util.statement.v2.Statement#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.statement.Statement#statement2String()
	 */
	@Override
	public String statement2String() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Statement.BEGIN).append(Statement.DELIM);
		buffer.append(lhsStatement.statement2String()).append(Statement.DELIM);
		buffer.append(operator.getStrVal()).append(Statement.DELIM);
		buffer.append(rhsStatement.statement2String()).append(Statement.DELIM);
		buffer.append(Statement.END);
		return buffer.toString();
	}

	@Override
	public StatementResult execute(Environment environment, boolean isDebug) {
		if (!isDebug) {
			return simpleExecute(environment);
		}
		List<Statement> reasons = executeForReason(environment);
		if (reasons.isEmpty()) {
			return StatementResult.resultForComplexStatements(true,
					StatementReason.EMPTY_REASON);
		} else {
			Map<String, Object> environmentEntries = extractUsedEnvironment(
					reasons, environment);
			return StatementResult.resultForComplexStatements(false,
					new StatementReason(reasons, environmentEntries));
		}
	}

	/**
	 * @param reasons
	 * @param environment
	 * @return
	 */
	private Map<String, Object> extractUsedEnvironment(List<Statement> reasons,
			Environment environment) {
		Map<String, Object> entries = new HashMap<String, Object>();
		List<String> keys = new ArrayList<String>();
		for (Statement stmt : reasons) {
			StatementUtils.listOutVariablesIterative(keys, stmt);
		}
		for (String key : keys) {
			Object variable = environment.getUncheckedVariable(key);
			entries.put(key, variable);
		}
		return entries;
	}

	/**
	 * @param environment
	 * @return
	 */
	private List<Statement> executeForReason(Environment environment) {
		List<Statement> reasons = new ArrayList<Statement>();

		for (Statement minSat : findMinimumSatisfaction()) {
			StatementResult minSatResult = minSat.execute(environment, false);
			if (!minSatResult.isSuccessful()) {
				minSat = StatementUtils.focusOnFalse(minSat, environment);
				reasons.add(minSat);
			} else {
				reasons.clear();
				return reasons;
			}
		}
		return reasons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.statement.Statement#findMinimumSatisfaction()
	 */
	@Override
	public Statement[] findMinimumSatisfaction() {
		if (!operator.isBoolOperator()) {
			return new Statement[] { this };
		}

		Statement[] _lhs = lhsStatement.findMinimumSatisfaction();
		Statement[] _rhs = rhsStatement.findMinimumSatisfaction();

		if (operator == EOperator.AND) {
			return doAndConjuction(_lhs, _rhs);
		} else if (operator == EOperator.OR) {
			return doOrConjuction(_lhs, _rhs);
		} else {
			throw new CorrectionIsNotPossibleException(
					"bool operator should either be \"AND\" or \"OR\"!");
		}

	}

	/**
	 * @param _lhs
	 * @param _rhs
	 * @return
	 */
	private Statement[] doOrConjuction(Statement[] _lhs, Statement[] _rhs) {
		int conjSize = _rhs.length + _lhs.length;
		Statement[] result = new Statement[conjSize];
		for (Statement _iLhs : _lhs) {
			result[--conjSize] = _iLhs;
		}
		for (Statement _iRhs : _rhs) {
			result[--conjSize] = _iRhs;
		}
		return result;
	}

	/**
	 * @param _lhs
	 * @param _rhs
	 * @return
	 */
	private Statement[] doAndConjuction(Statement[] _lhs, Statement[] _rhs) {
		int conjSize = _rhs.length * _lhs.length;
		Statement[] result = new Statement[conjSize];
		for (Statement _iRhs : _rhs) {
			for (Statement _iLhs : _lhs) {
				result[--conjSize] = new ComplexStatement(_iLhs, _iRhs,
						EOperator.AND);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.statement.Statement#getComplexityLevel()
	 */
	@Override
	public int getComplexityLevel() {
		int lhsComplexity = this.lhsStatement != null ? this.lhsStatement
				.getComplexityLevel()
				* COMPLEXITY_MULTIPLIER : COMPLEXITY_NULL;
		int rhsComplexity = this.rhsStatement != null ? this.rhsStatement
				.getComplexityLevel()
				* COMPLEXITY_MULTIPLIER : COMPLEXITY_NULL;
		int cenComplexity = this.centerStatement != null ? this.centerStatement
				.getComplexityLevel()
				* COMPLEXITY_MULTIPLIER : COMPLEXITY_NULL;
		return 1 + lhsComplexity + rhsComplexity + cenComplexity;
	}

	/**
	 * @return
	 */
	public boolean containsNestedComplexStatement() {
		return lhsStatement instanceof ComplexStatement
				|| rhsStatement instanceof ComplexStatement
				|| centerStatement instanceof ComplexStatement;
	}

	public Statement getLhsStatement() {
		return lhsStatement;
	}

	public Statement getRhsStatement() {
		return rhsStatement;
	}

	public Statement getCenterStatement() {
		return centerStatement;
	}

	public EOperator getOperator() {
		return operator;
	}

}
