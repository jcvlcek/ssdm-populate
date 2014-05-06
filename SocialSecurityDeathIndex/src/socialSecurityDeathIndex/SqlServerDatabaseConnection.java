/**
 * 
 */
package socialSecurityDeathIndex;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Makes a connection to an SQL Server database, using Microsoft's
 * <code>com.microsoft.sqlserver.jdbc.SQLServerDriver</code> driver.
 * @author Jim Vlcek
 */
public final class SqlServerDatabaseConnection extends DatabaseConnection {
	
	/**
	 * The sponsor (vendor) name for this database type
	 */
	public static final String SPONSOR = "SQL Server";
	/**
	 * The default IP port this database listens on
	 */
	public static final int DEFAULT_PORT = 1433;
	/**
	 * The default username for authentication to this database
	 */
	public static final String DEFAULT_DATABASE_USER = "root";
	/**
	 * The base URL for connections to databases of this type
	 */
	public static final String DEFAULT_URL_BASE = "jdbc:sqlserver://";
	/**
	 * The unique driver ID for connections to databases of this type
	 */
	public static final String DEFAULT_DATABASE_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	/**
	 * Creates a new instance of an SqlServerDatabaseConnection object
	 */
	public SqlServerDatabaseConnection() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the vendor / "sponsor" of the database implementation
	 * @return the name of the vendor / "sponsor" of the database implementation 
	 */
	public static String getSponsor() {
		return SPONSOR;
	}

	/**
	 * Instantiates the <code>com.microsoft.sqlserver.jdbc.SQLServerDriver</code> driver,
	 * and attempts to make a connection to the remote SQL Server database
	 * @param iPort the IP port on which the database server is listening
	 * @throws DbConnectionException if the required driver class cannot be instantiated
	 * @throws SQLException if an SQL error occurs when connecting to the remote database
	 */
	@Override
	public void ConnectToDatabase(int iPort) throws SQLException, DbConnectionException
	{
		if ( iPort == 0 )
			iPort = DEFAULT_PORT;
		String driver = DEFAULT_DATABASE_DRIVER;

		LoadDriver( driver );
		// TODO: Enable selection of integrated security or manual authentication
		// String sPassword = GetPassword( DEFAULT_DATABASE_USER );
		if ( true ) // ( sPassword != null )
		{
			// String user = getUsername();
			// String url = DEFAULT_URL_BASE + DEFAULT_DATABASE_HOST + ":" + String.valueOf( iPort ) + ";databaseName=" + DEFAULT_DATABASE_NAME + ";user=" + user + ";password=" + sPassword + ";";
			String url = DEFAULT_URL_BASE + DEFAULT_DATABASE_HOST + ":" + String.valueOf( iPort ) + ";databaseName=" + DEFAULT_DATABASE_NAME + ";integratedSecurity=true;";
			mConnection = DriverManager.getConnection(url);
		}
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#AddRecord(socialSecurityDeathIndex.IDeathRecord)
	 */
	@Override
	public void AddRecord(IDeathRecord drNew) throws DuplicateKeyException {
		super.AddRecord( drNew );
	}

}
