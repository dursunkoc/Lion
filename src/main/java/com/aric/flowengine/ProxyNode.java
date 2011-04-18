/**
 * 
 */
package com.aric.flowengine;

import java.util.List;

import com.aric.esb.ArgumentBundle;
import com.aric.esb.Caller;
import com.aric.esb.environment.Environment;
import com.aric.flowengine.repository.FlowRepository;
import com.aric.ruleengine.exceptions.NodeExecutionException;
import com.aric.ruleengine.statement.Statement;

/**
 * @author Dursun KOC
 * 
 */
public final class ProxyNode extends Node {
	private RealNode realNode;
	private FlowRepository persistenceBridge;

	/**
	 * @param id
	 * @param eof
	 * @param filter
	 * @param persistenceBridge
	 */
	public ProxyNode(String id, boolean eof, Statement filter,
			FlowRepository persistenceBridge) {
		super(id, eof, filter);
		this.persistenceBridge = persistenceBridge;
	}

	/**
	 * @param environment
	 */
	private synchronized void createRealNode(Environment environment) {
		if (realNode == null) {
			ArgumentBundle argumentBundle = persistenceBridge
					.getArgumentBundle(environment, this.getId());
			Caller caller = persistenceBridge.getCaller(this.getId());
			List<Node> children = persistenceBridge.getChildren(this.getId());
			Statement filter = this.getFilter();
			boolean eof = this.isEof();
			String id = this.getId();
			realNode = new RealNode(id, eof, filter, children, caller,
					argumentBundle);
		}
	}

	/**
	 * @param environment
	 * @return
	 */
	private RealNode getRealNode(Environment environment) {
		if (realNode == null) {
			createRealNode(environment);
		}
		return realNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.flow.Node#execute()
	 */
	@Override
	public Object execute(Environment environment) throws NodeExecutionException {
		RealNode realNode = getRealNode(environment);
		return realNode.execute(environment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.flow.Node#getNextNode(com.aric.esb.environment.
	 * Environment)
	 */
	@Override
	public Node getNextNode(Environment environment) {
		RealNode realNode = getRealNode(environment);
		return realNode.getNextNode(environment);
	}
}
