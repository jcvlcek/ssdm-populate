/**
 * 
 */
package socialSecurityDeathIndex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * @author vlcek
 *
 */
public final class MySqlDatabaseConnection extends DatabaseConnection {
	
	public static final String DEFAULT_DATABASE_HOST = "localhost";
	public static final int DEFAULT_PORT = 3306;
	public static final String DEFAULT_DATABASE_USER = "root";
	public static final String DEFAULT_DATABASE_NAME = "SSDI";
	public static final String DEFAULT_URL_BASE = "jdbc:mysql://";
	public static final String DEFAULT_DATABASE_DRIVER = "com.mysql.jdbc.Driver";

	/**
	 * 
	 */
	public MySqlDatabaseConnection() {
		// TODO Auto-generated constructor stub
	}
	
	public static int ConnectToDatabase( String sPassword )
	{
		String url = DEFAULT_URL_BASE + DEFAULT_DATABASE_HOST + ":" + String.valueOf( DEFAULT_PORT ) + "/";
		String user = DEFAULT_DATABASE_USER;
		String dbName = DEFAULT_DATABASE_NAME;
		String driver = DEFAULT_DATABASE_DRIVER;
		try {
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url + dbName, user, sPassword);
			conn.close();
			return 0;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Unable to connect to database \"" + dbName + "\"", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return -1;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

}
