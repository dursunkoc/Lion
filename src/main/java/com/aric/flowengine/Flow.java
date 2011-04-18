/**
 * 
 */
package com.aric.flowengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aric.esb.environment.Environment;
import com.aric.esb.environment.Variable;
import com.aric.esb.environment.VariableFactory;
import com.aric.esb.exceptions.CallerException;
import com.aric.flowengine.repository.FlowRepository;

/**
 * @author Dursun KOC
 * 
 */
public class Flow {
	private FlowRepository repository;

	/**
	 * @param repository
	 */
	public Flow(FlowRepository repository) {
		this.repository = repository;
	}

	/**
	 * 
	 * @param expectedVariables
	 * @param flowId
	 * @param environment
	 * @return
	 * @throws CallerException
	 */
	public FlowResult execute(List<String> expectedVariables, String flowId,
			Environment environment) {
		Map<String, Variable> resultMap;
		// get initial node from repository.
		Node currentNode = repository.getRootNode(flowId);
		try {
			// call each node in the flow by the rete algorithm
			currentNode = executeIterative(currentNode, environment);
			// extract requested variables from the environment
			resultMap = getResultMap(expectedVariables, environment);
			// return with success.
			FlowResult flowResult = new FlowResult(resultMap);
			return flowResult;
		} catch (Exception e) {
			// handle exception. repository may want to keep track of failure
			resultMap = repository.handleException(e, currentNode, environment);
			// return with fail.
			FlowResult flowResult = new FlowResult(resultMap, e);
			return flowResult;
		}
	}

	/**
	 * Extracts expected variables from the environmet.
	 * 
	 * @param expectedVariables
	 * @param environment
	 * @return
	 */
	private Map<String, Variable> getResultMap(List<String> expectedVariables,
			Environment environment) {

		Map<String, Variable> resultMap = new HashMap<String, Variable>();
		/*
		 * foreach expectedVariables get it from environment put into the
		 * resultMap
		 */
		for (String varName : expectedVariables) {
			Variable variable = environment.getUncheckedVariable(varName);
			resultMap.put(varName, variable);
		}
		return resultMap;
	}

	/**
	 * executes node after node by their filtering statement.
	 * 
	 * @param currentNode
	 * @param environment
	 * @return
	 * @throws CallerException
	 */
	private Node executeIterative(Node currentNode, Environment environment)
			throws CallerException {
		// call every node until EndOfFlow and get next node.
		while (!currentNode.isEOF()) {
			executeInternal(currentNode, environment);
			currentNode = currentNode.getNextNode(environment);
		}
		// execute the last node.
		executeInternal(currentNode, environment);
		return currentNode;
	}

	/**
	 * Executes node and push result of execution into environment
	 * 
	 * @param node
	 *            - to be executed
	 * @param environment
	 *            - in which the execution will take place, and result will be
	 *            pushed in.
	 * @throws CallerException
	 */
	private void executeInternal(Node node, Environment environment)
			throws CallerException {
		String nodeIdentifier = node.getId();
		Object nodeExecutionResult = node.execute(environment);
		Variable nodeResult = VariableFactory.newInstance(nodeExecutionResult);
		environment.addOrUpdateVariable(nodeIdentifier, nodeResult);
	}
}
