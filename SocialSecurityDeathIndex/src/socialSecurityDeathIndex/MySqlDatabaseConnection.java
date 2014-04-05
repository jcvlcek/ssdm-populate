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
public final class MySqlDatabaseConnection extends DatabaseConnection {
	public static final String SPONSOR = "MySQL";
	public static final int DEFAULT_PORT = 3306;
	public static final String DEFAULT_DATABASE_USER = "root";
	public static final String DEFAULT_URL_BASE = "jdbc:mysql://";
	public static final String DEFAULT_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * 
	 */
	public MySqlDatabaseConnection() {
		// TODO Auto-generated constructor stub
	}
	
	public void ConnectToDatabase( int iPort ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		if ( iPort == 0 )
			iPort = DEFAULT_PORT;
		String url = DEFAULT_URL_BASE + DEFAULT_DATABASE_HOST + ":" + String.valueOf( iPort ) + "/";
		String dbName = DEFAULT_DATABASE_NAME;
		String driver = DEFAULT_DATABASE_DRIVER;
		Class.forName(driver).newInstance();
		String sPassword = GetPassword( DEFAULT_DATABASE_USER );
		if ( sPassword != null )
		{
			String user = getUsername();
			mConnection = DriverManager.getConnection(url + dbName, user, sPassword);
		}
	}

	@Override
	public void AddRecord(IDeathRecord drNew) throws DuplicateKeyException {
		super.AddRecord(drNew);
	}
}
