/**
 * 
 */
package com.aric.flowengine;

import java.util.List;

import com.aric.esb.ArgumentBundle;
import com.aric.esb.Caller;
import com.aric.esb.environment.Environment;
import com.aric.esb.exceptions.CallerException;
import com.aric.ruleengine.exceptions.NodeExecutionException;
import com.aric.ruleengine.statement.Statement;

/**
 * @author Dursun KOC
 * 
 */
public final class RealNode extends Node {

	private List<Node> children;
	private Caller caller;
	private ArgumentBundle argumentBundle;

	/**
	 * @param id
	 * @param eof
	 * @param filter
	 * @param children
	 * @param caller
	 * @param argumentBundle
	 */
	public RealNode(String id, boolean eof, Statement filter,
			List<Node> children, Caller caller, ArgumentBundle argumentBundle) {
		super(id, eof, filter);
		this.children = children;
		this.caller = caller;
		this.argumentBundle = argumentBundle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.flow.Node#execute()
	 */
	@Override
	public Object execute(Environment environment)
			throws NodeExecutionException {
		try {
			Object result = this.caller.call(argumentBundle);
			return result;
		} catch (CallerException e) {
			throw new NodeExecutionException(e);
		}
	}

	public Node getNextNode(Environment environment) {
		for (Node node : children) {
			if (node.isValid(environment)) {
				return node;
			}
		}
		// TODO revise exception
		throw new RuntimeException("No Child!");
	}

}
