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
	
	public void ConnectToDatabase( ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		String url = DEFAULT_URL_BASE + DEFAULT_DATABASE_HOST + ":" + String.valueOf( DEFAULT_PORT ) + "/";
		String user = DEFAULT_DATABASE_USER;
		String dbName = DEFAULT_DATABASE_NAME;
		String driver = DEFAULT_DATABASE_DRIVER;
		Class.forName(driver).newInstance();
		String sPassword = GetPassword();
		if ( sPassword != null )
		{
			Connection conn = DriverManager.getConnection(url + dbName, user, sPassword);
			conn.close();
		}
	}
	
	public Boolean RecordExists( IDeathRecord drTarg )
	{
		return false;
	}
	
	public IDeathRecord Match( long SSAN )
	{
		return null;
	}

	@Override
	public void AddRecord(IDeathRecord drNew) throws DuplicateKeyException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("AddRecord not yet implemented for MySqlDatabaseConnection class");
	}

}
