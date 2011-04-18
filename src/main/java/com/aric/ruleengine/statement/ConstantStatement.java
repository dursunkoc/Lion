/**
 *
 */
package com.aric.ruleengine.statement;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;

import org.apache.geronimo.mail.util.StringBufferOutputStream;

import com.aric.esb.environment.Environment;

/**
 * @author Dursun KOC
 * 
 */
public class ConstantStatement implements Statement {
	private static final long serialVersionUID = 3220324825097470187L;
	private Serializable value;

	/**
	 * @param identifier
	 * @param value
	 */
	public ConstantStatement(Serializable value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.turkcelltech.comet.flow.util.statement.v2.Statement#execute(com.
	 * turkcelltech.comet.flow.engine.components.Environment, boolean)
	 */
	@Override
	public StatementResult execute(Environment environment, boolean isDebug) {
		return this.execute(environment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.turkcelltech.comet.flow.util.statement.v2.Statement#execute(com.
	 * turkcelltech.comet.flow.engine.components.Environment)
	 */
	@Override
	public StatementResult execute(Environment environment) {
		return StatementResult.resultForSimpleStatements(this.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.turkcelltech.comet.flow.util.statement.v2.Statement#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.statement.Statement#statement2String()
	 */
	@Override
	public String statement2String() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Statement.CONST_BEGIN);
		this.value2String(buffer);
		buffer.append(Statement.CONST_END);
		return buffer.toString();
	}

	/**
	 * @param buffer
	 */
	private void value2String(StringBuffer buffer) {
		try {
			StringBuffer buff = new StringBuffer();
			StringBufferOutputStream sbos = new StringBufferOutputStream(buff);
			ObjectOutputStream oos = new ObjectOutputStream(sbos);
			oos.writeObject(value);
			oos.flush();
			sbos.flush();
			oos.close();
			sbos.close();
			buffer.append(buff);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param serialized
	 * @return
	 * @throws IOException
	 */
	public static Statement buildFromString(String serialized) {
		try {
			final Reader stringReader = new StringReader(serialized);
			InputStream inputStream = new InputStream() {
				@Override
				public int read() throws IOException {
					return stringReader.read();
				}

			};
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			return new ConstantStatement((Serializable) ois.readObject());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.statement.Statement#findMinimumSatisfaction()
	 */
	@Override
	public Statement[] findMinimumSatisfaction() {
		return new Statement[] { this };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aric.ruleengine.statement.Statement#getComplexityLevel()
	 */
	@Override
	public int getComplexityLevel() {
		return COMPLEXITY_BASE;
	}

}
