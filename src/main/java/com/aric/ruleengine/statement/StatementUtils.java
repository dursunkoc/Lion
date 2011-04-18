/**
 *
 */
package com.aric.ruleengine.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.aric.esb.environment.Environment;
import com.aric.ruleengine.exceptions.InvalidStatementException;
import com.aric.ruleengine.operators.EOperator;
import com.aric.ruleengine.operators.OperatorFactory;

/**
 * @author Dursun KOC
 * 
 */
public class StatementUtils {
	/**
	 * @param complexStatement
	 * @return
	 */
	public static String statement2String(Statement statement) {
		return statement.statement2String();
	}

	/**
	 * @param command
	 * @return
	 */
	public static Statement string2Statement(String command) {
		if (command == null || command.isEmpty()) {
			return EmptyStatement.getInstance();
		}
		List<String> tokens = simplyParseSource(command);
		List<String> leftNotationTokens = buildLeftNotation(tokens);
		Statement finalStatement = buildExecutionStack(leftNotationTokens);
		return finalStatement;
	}

	private static List<String> simplyParseSource(String command) {
		StringBuffer buffer = new StringBuffer();
		List<String> tokens = new ArrayList<String>();

		for (int i = 0; i < command.length(); i++) {
			char c = command.charAt(i);
			if (isDelim(c, command, i)) {
				simplyAddToken(buffer, tokens);
				buffer = new StringBuffer();
			} else if (isConstantBegin(c, command, i)) {
				while (!isConstantEnd(command, i)) {
					buffer.append(command.charAt(i));
					i = i + 1;
				}
				buffer.append(command.charAt(i));
			} else if (isVariableBegin(c, command, i)) {
				while (!isVariableEnd(command, i)) {
					buffer.append(command.charAt(i));
					i = i + 1;
				}
				buffer.append(command.charAt(i));
			} else if (isComplexBeginOrComplexEnd(c, command, i)) {
				bracketAddToken(buffer, c, tokens);
				buffer = new StringBuffer();
			} else {
				buffer.append(c);
			}
		}
		if (buffer.length() > 0) {
			simplyAddToken(buffer, tokens);
		}
		return tokens;
	}

	/**
	 * @param c
	 * @return
	 */
	private static boolean isComplexBeginOrComplexEnd(char c, String command,
			int index) {
		return isComplexBegin(c, command, index)
				|| isComplexEnd(c, command, index);
	}

	/**
	 * @param command
	 * @return
	 */
	private static boolean isComplexBeginOrComplexEnd(String command) {
		return isComplexBegin(command) || isComplexEnd(command);
	}

	/**
	 * @param c
	 * @return
	 */
	private static boolean isComplexEnd(char c, String command, int index) {
		boolean isComplexEnd = (c == Statement.cEND && checkConformity(command,
				index, Statement.END));
		return isComplexEnd;
	}

	/**
	 * @param command
	 * @return
	 */
	private static boolean isComplexEnd(String command) {
		return command.equals(Statement.END);
	}

	/**
	 * @param c
	 * @return
	 */
	private static boolean isComplexBegin(char c, String command, int index) {
		boolean isComplexBegin = (c == Statement.cBEGIN && checkConformity(
				command, index, Statement.BEGIN));
		return isComplexBegin;
	}

	/**
	 * @param command
	 * @return
	 */
	private static boolean isComplexBegin(String command) {
		return command.equals(Statement.BEGIN);
	}

	/**
	 * @param command
	 * @param i
	 * @return
	 */
	private static boolean isVariableEnd(String command, int index) {
		boolean isVariableEnd = (command.charAt(index) == Statement.cVARDEF_END && checkConformity(
				command, index, Statement.VARDEF_END));
		return isVariableEnd;
	}

	/**
	 * @param c
	 * @return
	 */
	private static boolean isVariableBegin(char c, String command, int index) {
		boolean isVariableBegin = (c == Statement.cVARDEF_BEGIN && checkConformity(
				command, index, Statement.VARDEF_BEGIN));
		return isVariableBegin;
	}

	/**
	 * @param command
	 * @return
	 */
	private static boolean isVariable(String command) {
		return command.startsWith(Statement.VARDEF_BEGIN)
				&& command.endsWith(Statement.VARDEF_END);
	}

	/**
	 * @param command
	 * @param index
	 * @return
	 */
	private static boolean isConstantEnd(String command, int index) {
		boolean isConstantEnd = (command.charAt(index) == Statement.cCONST_END && checkConformity(
				command, index, Statement.CONST_END));
		return isConstantEnd;
	}

	/**
	 * @param c
	 * @return
	 */
	private static boolean isConstantBegin(char c, String command, int index) {
		boolean isConstantBegin = (c == Statement.cCONST_BEGIN && checkConformity(
				command, index, Statement.CONST_BEGIN));
		return isConstantBegin;
	}

	/**
	 * @param command
	 * @return
	 */
	private static boolean isConstant(String command) {
		return command.startsWith(Statement.CONST_BEGIN)
				&& command.endsWith(Statement.CONST_END);

	}

	/**
	 * @param c
	 * @return
	 */
	private static boolean isDelim(char c, String command, int index) {
		boolean isDelim = (c == Statement.cDELIM && checkConformity(command,
				index, Statement.DELIM));
		return isDelim;
	}

	/**
	 * @param command
	 * @param index
	 * @param conformityStr
	 * @return
	 */
	private static boolean checkConformity(String command, int index,
			String conformityStr) {
		return command.subSequence(index, index + conformityStr.length())
				.equals(conformityStr);
	}

	/**
	 * any straightForward Token
	 * 
	 * @param buffer
	 * @param tokens
	 */
	private static void simplyAddToken(StringBuffer buffer, List<String> tokens) {
		if (buffer.length() > 0) {
			tokens.add(buffer.toString());
		}
	}

	/**
	 * Tokenize brackets
	 * 
	 * @param buffer
	 * @param c
	 * @param tokens
	 */
	private static void bracketAddToken(StringBuffer buffer, char c,
			List<String> tokens) {
		simplyAddToken(buffer, tokens);
		tokens.add(Character.toString(c));
	}

	private static List<String> buildLeftNotation(List<String> tokens) {
		List<String> leftNotationTokens = new ArrayList<String>();
		// nothing to build
		if (tokens.size() == 0) {
			return leftNotationTokens;
		}

		Stack<String> stack = new Stack<String>();
		stack.add("");
		for (String token : tokens) {
			if (isComplexEnd(token)) {
				while (!isComplexBegin(token)) {
					token = stack.pop();
					if (!isComplexBegin(token)) {
						leftNotationTokens.add(token);
					}
				}
			} else if (isSymbol(token)) {
				if (isDecisionOperator(token)) {
					while (isStackPopAbleForExecution(stack)) {
						leftNotationTokens.add(stack.pop());
					}
				}
				stack.push(token);
			} else if (isComplexBegin(token)) {
				stack.push(token);
			} else {
				leftNotationTokens.add(token);
			}
		}
		while (isStackPopAbleForExecution(stack)) {
			String stackToken = stack.pop();
			leftNotationTokens.add(stackToken);
		}

		return leftNotationTokens;
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	private static boolean isSymbol(String token) {
		for (EOperator eOperator : EOperator.values()) {
			if (eOperator.isMe(token)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	private static boolean isDecisionOperator(String token) {
		return EOperator.AND.isMe(token) || EOperator.OR.isMe(token);
	}

	/**
	 * 
	 * @param stack
	 * @return
	 */
	private static boolean isStackPopAbleForExecution(Stack<String> stack) {
		return !(stack.isEmpty()) && !(stack.lastElement() == null)
				&& !(isComplexBeginOrComplexEnd(stack.lastElement()))
				&& !(stack.lastElement().equals(""));
	}

	private static Statement buildExecutionStack(List<String> leftNotationTokens) {
		Statement stmt;
		Stack<Statement> stmtStack = new Stack<Statement>();
		for (String token : leftNotationTokens) {
			if (isSymbol(token)) {
				stmt = createComplexStatement(stmtStack, token);
			} else if (isVariable(token)) {
				stmt = createVariableStatement(token);
			} else if (isConstant(token)) {
				stmt = createConstantStatement(token);
			} else if (isEmpty(token)) {
				stmt = EmptyStatement.getInstance();
			} else {
				throw new InvalidStatementException(
						"Broken statement. token :'" + token + "'");
			}
			stmtStack.push(stmt);
		}
		return stmtStack.pop();
	}

	/**
	 * @param stmtStack
	 * @param token
	 * @return
	 */
	private static Statement createComplexStatement(Stack<Statement> stmtStack,
			String token) {
		Statement stmt;
		Statement rhsStatement = stmtStack.pop();
		Statement lhsStatement = stmtStack.pop();
		EOperator operator = OperatorFactory.getEOperator(token);
		stmt = new ComplexStatement(lhsStatement, rhsStatement, operator);
		return stmt;
	}

	/**
	 * @param token
	 * @return
	 */
	private static Statement createVariableStatement(String token) {
		Statement stmt;
		String variableName = token.substring(Statement.VARDEF_BEGIN.length(),
				token.length() - Statement.VARDEF_END.length());
		stmt = new VariableStatement(variableName);
		return stmt;
	}

	/**
	 * @param token
	 * @return
	 */
	private static Statement createConstantStatement(String token) {
		Statement stmt;
		stmt = ConstantStatement.buildFromString(token.substring(
				Statement.CONST_BEGIN.length(), token.length()
						- Statement.CONST_END.length()));
		return stmt;
	}

	/**
	 * @param token
	 * @return
	 */
	private static boolean isEmpty(String token) {
		return token.equals(Statement.EMPTY_STATEMENT);
	}

	/**
	 * @param statement
	 * @param environment
	 */
	public static Statement focusOnFalse(Statement statement,
			Environment environment) {
		if (statement instanceof ComplexStatement) {
			ComplexStatement complexStmt = (ComplexStatement) statement;
			if (complexStmt.containsNestedComplexStatement()) {
				Statement _lhsReason = focusOnFalse(complexStmt
						.getLhsStatement(), environment);
				if (_lhsReason == null) {
					return complexStmt.getRhsStatement();
				}
				Statement _rhsReason = focusOnFalse(complexStmt
						.getRhsStatement(), environment);
				if (_rhsReason == null) {
					return complexStmt.getLhsStatement();
				}
				ComplexStatement finalReason = new ComplexStatement(_lhsReason,
						_rhsReason, complexStmt.getOperator());
				return finalReason;
			} else {
				StatementResult reasonResult = statement.execute(environment);
				return reasonResult.isSuccessful() ? null : statement;
			}
		}
		return statement;
	}

	/**
	 * @param outList
	 * @param stmt
	 */
	public static void listOutVariablesIterative(List<String> outList,
			Statement stmt) {
		if (stmt instanceof ComplexStatement) {
			ComplexStatement cmplx = (ComplexStatement) stmt;
			listOutVariablesIterative(outList, cmplx.getCenterStatement());
			listOutVariablesIterative(outList, cmplx.getLhsStatement());
			listOutVariablesIterative(outList, cmplx.getRhsStatement());
		} else if (stmt instanceof VariableStatement) {
			outList.add(((VariableStatement) stmt).getVariableName());
		}
	}
}
