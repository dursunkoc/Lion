/**
 * 
 */
package com.aric.ruleengine.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.aric.ruleengine.IRule;

/**
 * @author TCDUKOC
 * 
 */
public class SQLiteRuleRepository implements RuleRepository {
	private Connection conn;

	private SQLiteRuleRepository() {
		createConnectionAndDBStructure();
	}

	private void createConnectionAndDBStructure() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = getConnectionInternal();
			Statement stat = createStmt();
			stat.executeUpdate("create table if not exists"
					+ " truleNamespace ("
					+ "id integer primary key autoincrement, "
					+ "parentRuleId integer not null, "
					+ "namespacename varchar(150) not null" + ");");
			stat.executeUpdate("create table if not exists" + " trule ("
					+ "id integer primary key autoincrement, "
					+ "parentRuleId integer, " + "statement text, "
					+ "benefit numeric, " + "isInferable boolean not null, "
					+ "maxInferable integer not null, " + "optimizer integer"
					+ ");");
			stat.executeUpdate("create table if not exists" + " truleContext ("
					+ "id integer primary key autoincrement, "
					+ "ruleId integer not null, "
					+ "name varchar(150) not null, " + "value text" + ");");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Connection getConnectionInternal() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:RuleEngine.db");
	}

	public void createNewRuleNamespace(String ruleNamespace) {
		try {
			PreparedStatement prep = prepareStmt("replace into trule("
					+ "statement, " + "benefit, " + "isInferable, "
					+ "maxInferable) " + "values(?,?,?,?)");
			prep.setString(1, "");
			prep.setLong(2, 1L);
			prep.setBoolean(3, false);
			prep.setLong(4, 500L);
			prep.addBatch();
			executeBatch(prep);
			Statement stat = createStmt();
			ResultSet rs = stat.executeQuery("select max(id) from trule;");
			rs.next();
			long ruleId = rs.getLong(1);
			rs.close();

			prep = prepareStmt("replace into truleNamespace(parentRuleId,namespacename) values(?,?)");
			prep.setLong(1, ruleId);
			prep.setString(2, ruleNamespace);
			prep.addBatch();
			executeBatch(prep);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void executeBatch(PreparedStatement prep) throws SQLException {
		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);
	}

	private static final SQLiteRuleRepository instance = new SQLiteRuleRepository();

	/**
	 * @return
	 */
	public static SQLiteRuleRepository getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.ruleengine.persistence.RulePersistenceBridge#readChildren(com
	 * .aric.ruleengine.IRule)
	 */
	@Override
	public List<IRule> readChildren(IRule parent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.ruleengine.persistence.RulePersistenceBridge#readContext(java
	 * .lang.Long)
	 */
	@Override
	public Map<String, Object> readContext(Long identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aric.ruleengine.persistence.RulePersistenceBridge#readRootRule(java
	 * .lang.String)
	 */
	@Override
	public IRule readRootRule(String ruleNamespace) {
		// TODO Auto-generated method stub
		return null;
	}

	private Statement createStmt() {
		try {
			return conn.createStatement();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private PreparedStatement prepareStmt(String sql) {
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean close() {
		try {
			this.conn.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean reopen() {
		try {
			if (!this.conn.isClosed()) {
				this.conn = getConnectionInternal();
				return true;
			} else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

}
