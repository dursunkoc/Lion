package com.aric.flowengine;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aric.esb.environment.Environment;
import com.aric.esb.environment.VariableFactory;
import com.aric.flowengine.repository.DummyFlowRepository;
import com.aric.flowengine.repository.FlowRepository;
import com.aric.testutils.JndiCreator;

public class FlowTest {

	@Before
	public void setUp() throws Exception {
		JndiCreator.create(JndiCreator.DB_OPTION_MYSQL);
	}

	@After
	public void tearDown() throws Exception {
		JndiCreator.kill();
	}

	@Test
	public void testExecute() {
		FlowRepository repository = new DummyFlowRepository();
		Flow flow = new Flow(repository);
		String flowId = "myFlowId";
		Environment environment = new Environment();
		environment.addOrUpdateVariable("campaignId", VariableFactory.newInstance(1));
		environment.addOrUpdateVariable("campaignName", VariableFactory.newInstance("Larries Test Campaign"));
		FlowResult flowResult = flow.execute(
				Arrays.asList(new String[] { "campaignId", "offerId",
						"parameters" }), flowId, environment);
		System.out.println(flowResult);
	}

}
