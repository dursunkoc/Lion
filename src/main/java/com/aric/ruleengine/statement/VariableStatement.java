package com.aric.ruleengine.statement;

import com.aric.esb.environment.Environment;
import com.aric.esb.environment.Variable;
import com.aric.esb.util.DynamicCaller;

public class VariableStatement implements Statement {
	private static final long serialVersionUID = 8760976423646471617L;
	private String variableName;
	private String tail;

	/**
	 * @param variableName
	 */
	public VariableStatement(String variableWithTailName) {
		int varNameEndPoint = variableWithTailName
				.indexOf(VARDEF_NAME_TAIL_SEPERATOR);
		if (varNameEndPoint >= 0) {
			this.variableName = variableWithTailName.substring(0,
					varNameEndPoint);
			this.tail = variableWithTailName.substring(varNameEndPoint + 1);
		} else {
			this.variableName = variableWithTailName;
			this.tail = null;
		}
	}

	public String getVariableName() {
		return variableName;
	}

	public String getTail() {
		return tail;
	}

	@Override
	public StatementResult execute(Environment environment, boolean isDebug) {
		return this.execute(environment);
	}

	@Override
	public StatementResult execute(Environment environment) {
		Variable varObj = findOutVariable(environment);
		Object value = extractValueOfVariable(varObj);
		return StatementResult.resultForSimpleStatements(value);
	}

	/**
	 * @param environment
	 * @return
	 */
	private Variable findOutVariable(Environment environment) {
		Variable varObj = environment.getUncheckedVariable(variableName);
		if (varObj == null) {
			// TODO:implement me! varObj =
			// FunctionalNode.retreiveVariable(variableName,
			// environment);
		}
		return varObj;
	}

	private Object extractValueOfVariable(Variable varObj) {
		Object value = varObj.getValue();
		value = DynamicCaller.call(tail, value);
		return value;
	}

	@Override
	public Statement[] findMinimumSatisfaction() {
		return new Statement[] { this };
	}

	@Override
	public int getComplexityLevel() {
		return COMPLEXITY_BASE;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public String statement2String() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Statement.VARDEF_BEGIN);
		buffer.append(this.variableName);
		buffer.append(Statement.VARDEF_END);
		return buffer.toString();
	}

}
