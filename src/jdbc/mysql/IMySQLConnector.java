package jdbc.mysql;

import java.sql.Connection;

public interface IMySQLConnector {
	public Connection getConnection();
}
