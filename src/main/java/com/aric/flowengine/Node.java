/**
 * 
 */
package com.aric.flowengine;

import com.aric.esb.environment.Environment;
import com.aric.ruleengine.exceptions.NodeExecutionException;
import com.aric.ruleengine.statement.Statement;
import com.aric.ruleengine.statement.StatementResult;

/**
 * 
 * @author Dursun KOC
 * 
 */
public abstract class Node {
	private String id;
	private boolean eof;
	private Statement filter;

	/**
	 * @param id
	 * @param eof
	 * @param filter
	 */
	public Node(String id, boolean eof, Statement filter) {
		this.id = id;
		this.eof = eof;
		this.filter = filter;
	}

	/**
	 * if the current node has any children or not.
	 * 
	 * @return - is end of flow.
	 */
	public boolean isEOF() {
		return eof;
	}

	/**
	 * Gives the identifer for this node. Should be unique for the flow. <br>
	 * because it is pushed into the environment.
	 * 
	 * @return - unique identifier of the node.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return
	 */
	protected Statement getFilter() {
		return filter;
	}

	/**
	 * @return
	 */
	protected boolean isEof() {
		return eof;
	}

	/**
	 * Executes filter Statement.
	 * 
	 * @param environment
	 *            - in which the filter will executed in.
	 * @return - whether or not the filter is successfull.
	 */
	public boolean isValid(Environment environment) {
		StatementResult filterResult = filter.execute(environment);
		return filterResult.isSuccessful();
	}

	/**
	 * @param parent
	 * @param environment
	 * @return
	 */
	public abstract Node getNextNode(Environment environment);

	/**
	 * Executes the underlying ESB caller objects call method and returns its
	 * result.
	 * 
	 * @return
	 * @throws NodeExecutionException
	 */
	public abstract Object execute(Environment environment)
			throws NodeExecutionException;

}
