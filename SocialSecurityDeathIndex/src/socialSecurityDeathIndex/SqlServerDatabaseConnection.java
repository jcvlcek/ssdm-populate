/**
 * 
 */
package socialSecurityDeathIndex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author vlcek
 *
 */
public final class SqlServerDatabaseConnection extends DatabaseConnection {
	
	public static final int DEFAULT_PORT = 1433;
	public static final String DEFAULT_DATABASE_USER = "root";
	public static final String DEFAULT_DATABASE_NAME = "SSDI";
	public static final String DEFAULT_URL_BASE = "jdbc:sqlserver://";
	public static final String DEFAULT_DATABASE_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	/**
	 * 
	 */
	public SqlServerDatabaseConnection() {
		// TODO Auto-generated constructor stub
	}
	
	public void ConnectToDatabase( String sPassword ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		String url = DEFAULT_URL_BASE + DEFAULT_DATABASE_HOST + ";databaseName=" + DEFAULT_DATABASE_NAME + ";integratedSecurity=true;";
		String user = DEFAULT_DATABASE_USER;
		String dbName = DEFAULT_DATABASE_NAME;
		String driver = DEFAULT_DATABASE_DRIVER;
		Class.forName(driver).newInstance();
		Connection conn = DriverManager.getConnection(url);
		conn.close();
	}

}
