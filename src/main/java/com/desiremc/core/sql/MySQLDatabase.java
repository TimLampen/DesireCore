package com.desiremc.core.sql;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.Config;
import com.desiremc.core.util.logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class MySQLDatabase {
	
	private String host     = Config.MYSQL_HOST;
	private String database = Config.MYSQL_DATABASE;
	private String username = Config.MYSQL_USERNAME;
	private String password = Config.MYSQL_PASSWORD;
	private Connection connection;
	private static final String INSERT = "INSERT INTO :table VALUES(:total);";
	private static final String SELECT = "SELECT :fields FROM :table WHERE :fieldname=:value;";
	private static final String UPDATE = "UPDATE :table SET :fields WHERE :fieldname=:value;";

	public MySQLDatabase() {
		connect();
	}

	public Connection getConnection() {
		return connection;
	}
	
	private void connect() {
		String connectionUrl = "jdbc:mysql://" + host + ":3306/" + database;
		try {
			connection = DriverManager.getConnection(connectionUrl, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public MySQLResponse executeQuery(String query, Object... sets) {
		try {
			if (connection.isClosed()) {
				connect();
			}
		} catch (Exception localException) {
		}


		try {
			PreparedStatement statement = connection.prepareStatement(query);

			int index = 1;
			if (sets != null) for (Object obj : sets) {
				statement.setObject(index, obj);

				index += 1;
			}

			return new MySQLResponse(statement, statement.executeQuery());
		} catch (Exception localException) {
			localException.printStackTrace();

			return null;
		}
	}

	public MySQLResponse execute(String query, Object... sets) {
		try {
			if (connection.isClosed()) {
				connect();
			}
		} catch (Exception localException) {
		}
		try {
			PreparedStatement statement = connection.prepareStatement(query);

			int index = 1;
			if (sets != null) for (Object obj : sets) {
				statement.setObject(index, obj);

				index += 1;
			}

			statement.execute();

			return new MySQLResponse(statement);
		} catch (Exception localException) {
			localException.printStackTrace();

			return null;
		}
	}

	public MySQLResponse executeUpdate(String query, Object... sets) {
		try {
			if (connection.isClosed()) {
				connect();
			}
		} catch (Exception localException) {
		}
		try {
			PreparedStatement statement = connection.prepareStatement(query);

			int index = 1;
			if (sets != null) for (Object obj : sets) {
				statement.setObject(index, obj);

				index += 1;
			}

			statement.executeUpdate();

			return new MySQLResponse(statement);
		} catch (Exception localException) {
			localException.printStackTrace();

			return null;
		}
	}

	public MySQLResponse exists(String table, String fieldName, String value, String fields) {
		try {
			if (connection.isClosed()) {
				connect();
			}
		} catch (Exception localException) {
		}
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(select(fields, table, fieldName, "?"));
			statement.setString(1, value);
			ResultSet set = statement.executeQuery();

			return new MySQLResponse(statement, set, set.next());
		} catch (Exception localException) {
			localException.printStackTrace();

			return new MySQLResponse(statement, null, false);
		}
	}
	
	public String update(String table, List<String> fields, String fieldName, String value) {
		String fieldString = "";

		int index = 0;
		for (String field : fields) {
			if (index == 0) fieldString += field + "=?";
			else fieldString += ", " + field + "=?";
			index += 1;
		}

		return UPDATE.replace(":table", table).replace(":fields", fieldString).replace(":fieldname", fieldName).replace(":value", value);
	}

	public String insert(String table, int unknowns) {
		String total = "";

		for (int i = 0; i < unknowns; i++) {
			if (i == 0) total += "?";
			else total += ", ?";
		}

		return INSERT.replace(":table", table).replace(":total", total);
	}
	
	public String select(String fields, String table, String fieldName, String value) {
		return SELECT.replace(":fields", fields).replace(":table", table).replace(":fieldname", fieldName).replace(":value", value);
	}

}
