/**
 *
 */
package com.aric.ruleengine;

import java.util.Map;

import com.aric.esb.environment.Environment;
import com.aric.ruleengine.exceptions.InvalidRuleConstruction;
import com.aric.ruleengine.exceptions.InvalidStatementResultException;
import com.aric.ruleengine.optimizer.Optimizer;
import com.aric.ruleengine.repository.RuleRepository;
import com.aric.ruleengine.statement.EmptyStatement;
import com.aric.ruleengine.statement.Statement;
import com.aric.ruleengine.statement.StatementReason;
import com.aric.ruleengine.statement.StatementResult;

/**
 * @author Dursun KOC
 * 
 */
public class NeutralRule implements IRule {
	/**
	 * the context of the Rule...
	 */
	private Map<String, Object> readContext;
	/**
	 * Determines whether the rule is successfull or not
	 */
	private Statement statement;
	/**
	 * Maximum number of Inferable Context from the whole children, and grand
	 * children.
	 */
	private Integer maxInferable;

	/**
	 * The value of that context. Meaningful for inferableContexts
	 */
	private Long benefit;

	/**
	 * Inferable context are to be extended to the customer at the server
	 * runtime.
	 */
	private boolean isInferable;

	/**
	 * Global identifier for the rule instance.
	 */
	private Long identifier;

	/**
	 *
	 */
	private Optimizer optimizer;

	/**
	 * @param statement
	 *            if null then EmptyStatement
	 * @param maxInferable
	 *            if null then Integer.MaxValue
	 * @param benefit
	 *            null value should be handled...
	 * @param isInferable
	 *            true or false
	 * @param identifier
	 * @param optimizer
	 *            null value should be handled...
	 */
	public NeutralRule(Statement statement, Integer maxInferable, Long benefit,
			boolean isInferable, Long identifier, Optimizer optimizer) {
		this.statement = statement == null ? EmptyStatement.getInstance()
				: statement;
		this.maxInferable = maxInferable == null ? Integer.MAX_VALUE
				: maxInferable;
		this.benefit = benefit == null ? 0L : benefit;
		this.isInferable = isInferable;
		this.identifier = identifier;
		this.optimizer = optimizer;
		checkRuleSemantics(this.maxInferable, this.isInferable);
	}

	/**
	 * @param maxInferable
	 * @param isInferable
	 */
	private void checkRuleSemantics(Integer maxInferable, boolean isInferable) {
		checkInferableShouldNotHaveChild(maxInferable, isInferable);
		checkNonInferablesShouldHaveChild(maxInferable, isInferable);
	}

	/**
	 * @param maxInferable
	 * @param isInferable
	 */
	private void checkNonInferablesShouldHaveChild(Integer maxInferable,
			boolean isInferable) {
		if (!isInferable && maxInferable < 1) {
			throw new InvalidRuleConstruction(
					"A Rule cannot built-up with a max-inferable parameter smaller than one; when it is not inferable!");
		}
	}

	/**
	 * @param maxInferable
	 * @param isInferable
	 */
	private void checkInferableShouldNotHaveChild(Integer maxInferable,
			boolean isInferable) {
		if (isInferable && maxInferable != 1) {
			throw new InvalidRuleConstruction(
					"A Rule cannot be inferable when max-inferable parameter is not one!");
		}
	}

	/**
	 * @param ruleSniffer
	 */
	public void hostSniffer(RuleSniffer ruleSniffer) {
		ruleSniffer.cursorDown(this.maxInferable);
		try {
			StatementResult stmtResult = executeIn(ruleSniffer);
			boolean isResultSuccessfull = isResultSuccessfull(stmtResult);
			if (isResultSuccessfull) {
				ruleSniffer.sniff(this);
			} else {
				ruleSniffer.sniff(this, stmtResult.getReason());
			}
		} finally {
			ruleSniffer.cursorUp();
		}
	}

	/**
	 * @param ruleSniffer
	 * @return
	 */
	public StatementResult executeIn(RuleSniffer ruleSniffer) {
		Environment env = ruleSniffer.getEnvironment();
		StatementResult stmtResult = this.statement.execute(env);
		return stmtResult;
	}

	/**
	 * @param stmtResult
	 * @return
	 */
	private boolean isResultSuccessfull(StatementResult stmtResult) {
		Object result = stmtResult.getResult();
		if (result instanceof Boolean) {
			Boolean bool = (Boolean) result;
			return bool;
		}
		throw new InvalidStatementResultException(
				"The type statement's result should be Boolean!");
	}

	/**
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return this.identifier;
	}

	/**
	 * @return the maxInferable
	 */
	public Integer getMaxContext() {
		return this.maxInferable;
	}

	/**
	 * @return the optimizer
	 */
	public Optimizer getArbitrator() {
		return this.optimizer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.RuleContext#getBenefit
	 * ()
	 */
	@Override
	public Long getBenefit() {
		return this.benefit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.RuleContext#getReason()
	 */
	@Override
	public StatementReason getReason() {
		throw new UnsupportedOperationException(
				"NeutralRuleContext does not contain any reason!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.RuleContext#isNegative
	 * ()
	 */
	@Override
	public boolean isNegative() {
		throw new UnsupportedOperationException("NeutralRule is neutral!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.rulecontext.RuleContext
	 * #isInferable()
	 */
	@Override
	public boolean isInferable() {
		return this.isInferable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.turkcelltech.comet.flow.engine.ruleengine.v2.IRule#retreiveContextBody
	 * (com.turkcelltech.comet.flow.engine.ruleengine.v2.rulereader.RuleReader)
	 */
	@Override
	public Map<String, Object> retreiveContextBody(RuleRepository bridge) {
		if (readContext == null) {
			readContext = bridge.readContext(this.identifier);
		}
		return readContext;
	}

}
