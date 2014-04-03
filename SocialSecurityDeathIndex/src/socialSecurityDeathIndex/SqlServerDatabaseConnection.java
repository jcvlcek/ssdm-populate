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
	public static final String DEFAULT_URL_BASE = "jdbc:sqlserver://";
	public static final String DEFAULT_DATABASE_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	/**
	 * 
	 */
	public SqlServerDatabaseConnection() {
		// TODO Auto-generated constructor stub
	}
	
	public void ConnectToDatabase(int iPort) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		if ( iPort == 0 )
			iPort = DEFAULT_PORT;
		String dbName = DEFAULT_DATABASE_NAME;
		String driver = DEFAULT_DATABASE_DRIVER;
		Class.forName(driver).newInstance();
		String sPassword = GetPassword( DEFAULT_DATABASE_USER );
		if ( sPassword != null )
		{
			String user = getUsername();
			// String url = DEFAULT_URL_BASE + DEFAULT_DATABASE_HOST + ":" + String.valueOf( iPort ) + ";databaseName=" + DEFAULT_DATABASE_NAME + ";user=" + user + ";password=" + sPassword + ";";
			String url = DEFAULT_URL_BASE + DEFAULT_DATABASE_HOST + ":" + String.valueOf( iPort ) + ";databaseName=" + DEFAULT_DATABASE_NAME + ";integratedSecurity=true;";
			mConnection = DriverManager.getConnection(url);
		}
	}

	@Override
	public void AddRecord(IDeathRecord drNew) throws DuplicateKeyException {
		super.AddRecord( drNew );
	}

}
