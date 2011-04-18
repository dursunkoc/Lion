/**
 *
 */
package com.aric.ruleengine.statement;

import java.io.Serializable;

import com.aric.esb.environment.Environment;

/**
 * @author Dursun KOC
 * 
 */
public interface Statement extends Serializable {
	public static final int COMPLEXITY_NULL = 0;
	public static final int COMPLEXITY_BASE = 1;
	public static final int COMPLEXITY_MULTIPLIER = 10;
	public static final String BEGIN = "(";
	public static final String END = ")";
	public static final String DELIM = " ";
	public static final String VARDEF_BEGIN = "${";
	public static final String VARDEF_NAME_TAIL_SEPERATOR = ".";
	public static final String VARDEF_END = "}";
	public static final String CONST_BEGIN = "[";
	public static final String CONST_END = "]";
	public static final String CONST_VALSEP = "¨";
	public static final String CONST_FORMATINDICATOR = "|";
	public static final String CONST_TYPEINDICATOR = ">";
	public static final String EMPTY_STATEMENT = Character.toString((char) 238);

	public static final char cBEGIN = BEGIN.charAt(0);
	public static final char cEND = END.charAt(0);
	public static final char cDELIM = DELIM.charAt(0);
	public static final char cVARDEF_BEGIN = VARDEF_BEGIN.charAt(0);
	public static final char cVARDEF_END = VARDEF_END.charAt(0);
	public static final char cCONST_BEGIN = CONST_BEGIN.charAt(0);
	public static final char cCONST_END = CONST_END.charAt(0);
	public static final char cCONST_VALSEP = CONST_VALSEP.charAt(0);
	public static final char cCONST_FORMATINDICATOR = CONST_FORMATINDICATOR
			.charAt(0);
	public static final char cCONST_TYPEINDICATOR = CONST_TYPEINDICATOR
			.charAt(0);

	/**
	 * @param environment
	 * @param isDebug
	 * @return
	 */
	public abstract StatementResult execute(Environment environment,
			boolean isDebug);

	/**
	 * @param environment
	 * @return
	 */
	public abstract StatementResult execute(Environment environment);

	/**
	 * @return
	 */
	public abstract boolean isEmpty();

	/**
	 * @return
	 */
	public abstract String statement2String();

	/**
	 * @return
	 */
	public abstract Statement[] findMinimumSatisfaction();

	/**
	 * @return
	 */
	public abstract int getComplexityLevel();

}
