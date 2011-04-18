/**
 * 
 */
package com.aric.flowengine.repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.aric.esb.ArgumentBundle;
import com.aric.esb.Caller;
import com.aric.esb.arguments.Argument;
import com.aric.esb.arguments.SimpleArgument;
import com.aric.esb.config.RealReturnMap;
import com.aric.esb.config.ReturnMap;
import com.aric.esb.db.DBFunctionCaller;
import com.aric.esb.db.DBProcedureCaller;
import com.aric.esb.environment.Environment;
import com.aric.esb.environment.Variable;
import com.aric.esb.exceptions.CallerException;
import com.aric.esb.resourcefactory.DBResourceFactory;
import com.aric.flowengine.Node;
import com.aric.flowengine.ProxyNode;
import com.aric.ruleengine.statement.EmptyStatement;

/**
 * @author Dursun KOC
 * 
 */
public final class DummyFlowRepository implements FlowRepository {

	private static final String getCampaignName = "getCampaignName";
	private static final String createCampaign = "createCampaign";
	private DBResourceFactory dbResourceFactory;

	public DummyFlowRepository() {
		try {
			Context context = new InitialContext();
			Properties properties = new Properties();
			properties.putAll(context.getEnvironment());
			dbResourceFactory = new DBResourceFactory(properties);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.flowengine.repository.FlowRepository#getArgumentBundle(com.aric
	 * .esb.environment.Environment, java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ArgumentBundle getArgumentBundle(Environment environment,
			String nodeId) {
		Argument[] arguments;
		if (nodeId.equals(getCampaignName)) {
			Variable campaignIdVariable = environment
					.getUncheckedVariable("campaignId");
			arguments = new Argument[] { new SimpleArgument("id",
					campaignIdVariable.getValue()) };
		} else {
			Variable campaignIdVariable = environment
			.getUncheckedVariable("campaignId");
			Variable campaignNameVariable = environment
			.getUncheckedVariable("campaignName");
			arguments = new Argument[] {
					new SimpleArgument("name", campaignNameVariable.getValue()),
					new SimpleArgument("startDate", new Date()),
					new SimpleArgument("endDate", new Date(23, 12, 2012)),
					new SimpleArgument("isAbstract", 2),
					new SimpleArgument("id", campaignIdVariable.getValue()) };
		}

		return new ArgumentBundle(arguments);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.flowengine.repository.FlowRepository#getCaller(java.lang.String)
	 */
	@Override
	public Caller getCaller(String nodeId) {
		String connectionName = "java:MyApp/myDS";
		if (nodeId.equals(getCampaignName)) {
			ReturnMap returnMap = new RealReturnMap();
			returnMap.put(1, Types.VARCHAR);
			try {
				return new DBFunctionCaller(getCampaignName, returnMap,
						dbResourceFactory, connectionName);
			} catch (CallerException e) {
				return null;
			}
		} else if (nodeId.equals(createCampaign)) {
			ReturnMap returnMap = new RealReturnMap();
			returnMap.put(1, java.sql.Types.VARCHAR);
			returnMap.put(5, java.sql.Types.NUMERIC);
			try {
				return new DBProcedureCaller(createCampaign, returnMap,
						dbResourceFactory, connectionName);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.flowengine.repository.FlowRepository#getChildren(java.lang.String
	 * )
	 */
	@Override
	public List<Node> getChildren(String id) {
		Node node = new ProxyNode(createCampaign, true,
				EmptyStatement.getInstance(), this);
		return Arrays.asList(new Node[] { node });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.flowengine.repository.FlowRepository#getRootNode(java.lang.String
	 * )
	 */
	@Override
	public Node getRootNode(String flowId) {
		ProxyNode node = new ProxyNode(getCampaignName, false,
				EmptyStatement.getInstance(), this);
		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.flowengine.repository.FlowRepository#handleException(java.lang
	 * .Exception, com.aric.flowengine.Node,
	 * com.aric.esb.environment.Environment)
	 */
	@Override
	public Map<String, Variable> handleException(Exception e, Node currentNode,
			Environment environment) {
		throw new RuntimeException(e);
	}

}
