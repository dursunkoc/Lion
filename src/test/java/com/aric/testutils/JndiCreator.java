package com.aric.testutils;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

/**
 * @author TTDKOC
 * 
 */
public class JndiCreator {
	public static final int DB_OPTION_ORACLE = 1;
	public static final int DB_OPTION_MYSQL = 2;
	private static final String oracleUrl = "jdbc:oracle:thin:@10.201.248.30:3201:CAMPDBD";
	private static final String oracleUsername = "COMET";
	private static final String oraclePassword = "COMET";
	private static final String oracleDriverClassName = "oracle.jdbc.driver.OracleDriver";

	private static final String mysqlUrl = "jdbc:mysql://localhost:3306/test";
	private static final String mysqlUsername = "root";
	private static final String mysqlPassword = "42414241";
	private static final String mysqlDriverClassName = "com.mysql.jdbc.Driver";

	private static final SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();

	public static void create(int dbOption) throws Exception {
		try {
			Properties props;
			props = getDBProps(dbOption);
			DataSource ds = BasicDataSourceFactory.createDataSource(props);
			builder.bind("java:MyApp/myDS", ds);
			builder.activate();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
	}

	private static Properties getDBProps(int dbOption) {
		if (dbOption == DB_OPTION_ORACLE) {
			return getOracleSettings();
		} else if (dbOption == DB_OPTION_MYSQL) {
			return getMysqlSettings();
		} else {
			throw new RuntimeException("Unknown DB Option.");
		}
	}

	public static void kill() {
		builder.deactivate();
	}

	private static Properties getMysqlSettings() {
		Properties props = new Properties();
		props.put("url", mysqlUrl);
		props.put("username", mysqlUsername);
		props.put("password", mysqlPassword);
		props.put("driverClassName", mysqlDriverClassName);
		return props;
	}

	private static Properties getOracleSettings() {
		Properties props = new Properties();
		props.put("url", oracleUrl);
		props.put("username", oracleUsername);
		props.put("password", oraclePassword);
		props.put("driverClassName", oracleDriverClassName);
		return props;
	}
}