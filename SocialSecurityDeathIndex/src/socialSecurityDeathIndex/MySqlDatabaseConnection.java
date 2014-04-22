/**
 * 
 */
package socialSecurityDeathIndex;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Makes a connection to a MySQL database, using Oracle's
 * <code>com.mysql.jdbc.Driver</code> driver.
 * @author Jim Vlcek
 */
public final class MySqlDatabaseConnection extends DatabaseConnection {
	/**
	 * The sponsor (vendor) name for this database type
	 */
	public static final String SPONSOR = "MySQL";
	/**
	 * The default IP port this database listens on
	 */
	public static final int DEFAULT_PORT = 3306;
	/**
	 * The default username for authentication to this database
	 */
	public static final String DEFAULT_DATABASE_USER = "root";
	/**
	 * The base URL for connections to databases of this type
	 */
	public static final String DEFAULT_URL_BASE = "jdbc:mysql://";
	/**
	 * The unique driver ID for connections to databases of this type
	 */
	public static final String DEFAULT_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * Creates a new instance of a MySqlDatabaseConnection object
	 */
	public MySqlDatabaseConnection() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#getSponsor()
	 */
	@Override
	public String getSponsor() {
		return SPONSOR;
	}

	/**
	 * Instantiates the <code>com.mysql.jdbc.Driver</code> driver,
	 * and attempts to make a connection to the remote MySQL database
	 * @param iPort the IP port on which the database server is listening
	 * @throws InstantiationException if the MySQL driver cannot be instantiated
	 * @throws IllegalAccessException if the user has insufficient access privileges to instantiate and exercise the MySQL connection driver
	 * @throws ClassNotFoundException if the MySQL driver class cannot be found
	 * @throws SQLException if an SQL error occurs when connecting to the remote database
	 */
	@Override
	public void ConnectToDatabase( int iPort ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		if ( iPort == 0 )
			iPort = DEFAULT_PORT;
		String url = DEFAULT_URL_BASE + DEFAULT_DATABASE_HOST + ":" + String.valueOf( iPort ) + "/";
		String dbName = DEFAULT_DATABASE_NAME;
		String driver = DEFAULT_DATABASE_DRIVER;
		// TODO: newInstance considered evil: http://stackoverflow.com/questions/195321/why-is-class-newinstance-evil
		// Replace it with a better approach
		Class.forName(driver).newInstance();
		String sPassword = GetPassword( DEFAULT_DATABASE_USER );
		if ( sPassword != null )
		{
			String user = getUsername();
			mConnection = DriverManager.getConnection(url + dbName, user, sPassword);
		}
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#AddRecord(socialSecurityDeathIndex.IDeathRecord)
	 */
	@Override
	public void AddRecord(IDeathRecord drNew) throws DuplicateKeyException {
		super.AddRecord(drNew);
	}
}
