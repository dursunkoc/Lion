/**
 *
 */
package com.aric.ruleengine.statement;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Dursun KOC
 * 
 */
public class StatementReason {
	private List<Statement> statements;
	private Map<String, Object> environmentEntries;
	public static final StatementReason EMPTY_REASON = new StatementReason(
			null, null);

	/**
	 * @param statements
	 * @param environmentEntries
	 */
	@SuppressWarnings("unchecked")
	public StatementReason(List<Statement> statements,
			Map<String, Object> environmentEntries) {

		this.statements = statements == null ? Collections.EMPTY_LIST
				: statements;
		this.environmentEntries = environmentEntries == null ? Collections.EMPTY_MAP
				: environmentEntries;
	}

	/**
	 * @return the environmentEntries
	 */
	public Map<String, Object> getEnvironmentEntries() {
		return this.environmentEntries;
	}

	/**
	 * @return the statements
	 */
	public List<Statement> getStatements() {
		return this.statements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		for (Statement stmt : statements) {
			buffer.append(stmt.statement2String());
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
