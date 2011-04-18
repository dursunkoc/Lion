/**
 * 
 */
package com.aric.flowengine.repository;

import java.util.List;
import java.util.Map;

import com.aric.esb.ArgumentBundle;
import com.aric.esb.Caller;
import com.aric.esb.environment.Environment;
import com.aric.esb.environment.Variable;
import com.aric.flowengine.Node;

/**
 * @author Dursun KOC
 * 
 */
public interface FlowRepository {

	/**
	 * @param environment
	 * @param nodeId
	 * @return
	 */
	public ArgumentBundle getArgumentBundle(Environment environment,
			String nodeId);

	/**
	 * @param nodeId
	 * @return
	 */
	public Caller getCaller(String nodeId);

	/**
	 * @param nodeId
	 * @return
	 */
	public List<Node> getChildren(String nodeId);

	/**
	 * @param flowId
	 * @return
	 */
	public Node getRootNode(String flowId);

	/**
	 * @param e
	 * @param currentNode
	 * @param environment
	 * @return
	 */
	public Map<String, Variable> handleException(Exception e, Node currentNode,
			Environment environment);

}
