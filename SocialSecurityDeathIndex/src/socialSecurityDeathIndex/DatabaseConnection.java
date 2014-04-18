/**
 * 
 */
package socialSecurityDeathIndex;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author vlcek
 */
/**
 * A default implementation of the IDatabaseConnection interface.<br>
 * Subclasses inherit from DatabaseConnection to establish a connection
 * to a database from a specific vendor (sponsor).<br>
 * These subclasses must implement at least the ConnectToDatabase method.
 */
public abstract class DatabaseConnection implements IDatabaseConnection {

	/**
	 * The hostname or IP address of the database server
	 */
	public static final String DEFAULT_DATABASE_HOST = "localhost";
	/**
	 * The default database name for the Social Security Death Index tables
	 */
	public static final String DEFAULT_DATABASE_NAME = "SSDI";
	/**
	 * Last / family / surname column name in the database table
	 */
	public static final String LAST_NAME_COLUMN = "LASTNAME";
	/**
	 * First / given name column name in the database table
	 */
	public static final String FIRST_NAME_COLUMN = "FIRSTNAME";
	/**
	 * Middle name column name in the database table
	 */
	public static final String MIDDLE_NAME_COLUMN = "MIDDLENAME";
	/**
	 * Earliest birth date column name in the database table
	 */
	public static final String EARLIEST_BIRTH_DATE_COLUMN = "BIRTHBEGIN";
	/**
	 * Latest birth date column name in the database table
	 */
	public static final String LATEST_BIRTH_DATE_COLUMN = "BIRTHEND";
	/**
	 * Earliest death date column name in the database table
	 */
	public static final String EARLIEST_DEATH_DATE_COLUMN = "DEATHBEGIN";
	/**
	 * Latest death date column name in the database table
	 */
	public static final String LATEST_DEATH_DATE_COLUMN = "DEATHEND";
	/**
	 * Social Security Account Number column name in the database table
	 */
	public static final String SSAN_COLUMN = "SSAN";
	
	private static final String LINE_SEPARATOR = System.getProperty( "line.separator");
	private static final DateFormat mDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

	private String mDatabaseName;
	private String mHostname;
	private String mUsername;
	private int mPort;

	protected Connection mConnection = null;

	/**
	 * Default constructor for the DatabaseConnection class
	 */
	public DatabaseConnection() {
		mHostname = DEFAULT_DATABASE_HOST;
		mDatabaseName = DEFAULT_DATABASE_NAME;
		mPort = 0;
	}
	
	/**
	 * Create a connection corresponding to the provided sponsor specification
	 * @param sSponsor the sponsor (vendor, etc.) of the database to be connected
	 * @return an as-yet unconnected database connection
	 */
	public static IDatabaseConnection CreateConnection( String sSponsor )
	{
		// Still an ugly, switch-statement-based approach,
		// but at least it's here and not in SSDIprogram
		// TODO: Use Java's ServiceLoader mechanism instead
		if ( sSponsor.equalsIgnoreCase(MySqlDatabaseConnection.SPONSOR))
			return new MySqlDatabaseConnection();
		else if ( sSponsor.equalsIgnoreCase(SqlServerDatabaseConnection.SPONSOR) )
			return new SqlServerDatabaseConnection();
		else // if ( sSponsor.equalsIgnoreCase(BeanDatabaseConnection.SPONSOR))
			return new BeanDatabaseConnection();
		// TODO Else we should actually throw an appropriate exception
	}
	
	/**
	 * Prompt the user to supply a username and password for the database
	 * @return the user-supplied password for the database (null if the user cancels the operation)
	 */
	public String GetPassword( String sDefaultUser )
	{
		//Using a JPanel as the message for the JOptionPane
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new GridLayout(2,2));

		//Labels for the textfield components        
		JLabel lblUsername = new JLabel("Username:");
		JLabel lblPassword = new JLabel("Password:");

		JTextField username = new JTextField();
		username.setText(sDefaultUser);
		JPasswordField fldPassword = new JPasswordField();

		//Add the components to the JPanel        
		userPanel.add(lblUsername);
		userPanel.add(username);
		userPanel.add(lblPassword);
		userPanel.add(fldPassword);
		
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, userPanel, "Log in to database",
		                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                         null, options, options[0]);
		if (option == JOptionPane.OK_OPTION) // pressing OK button
		{
			setUsername( username.getText() );
		    return new String( fldPassword.getPassword() );
		}
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#Connect()
	 */
	@Override
	public void Connect() throws DbConnectionException {
		try {
			ConnectToDatabase( mPort );
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Unable to connect to database \"" + getDatabaseName() + "\"", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Required database connection class not loaded:" + LINE_SEPARATOR + e.getMessage(), "Unable to connect to database \"" + getDatabaseName() + "\"", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#Connect(java.lang.Integer)
	 */
	@Override
	public void Connect( int iPort )
			throws DbConnectionException {
		mPort = iPort;
		Connect();
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#Connect(java.lang.String, java.lang.String)
	 */
	@Override
	public void Connect(String Hostname, String database)
			throws DbConnectionException {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#Disconnect()
	 */
	@Override
	public void Disconnect()
	{
		if ( mConnection != null )
		{
			try {
				mConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				mConnection = null;		
			}
		}
	}
	
	/**
	 * Execute a database-specific connection
	 * @param iPort TCP port to connect to
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public abstract void ConnectToDatabase( int iPort ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	
	/**
	 * Get the name of the database on the remote server
	 */
	public String getDatabaseName()
	{
		return mDatabaseName;
	}
	
	/**
	 * Set the name of the database on the remote server
	 * @param sDatabase new name of the database
	 */
	protected void setDatabaseName( String sDatabase )
	{
		mDatabaseName = sDatabase;
	}

	/**
	 * Get the name or IP address of the remote database server
	 */
	public String getHostname()
	{
		return mHostname;
	}
	
	/**
	 * Set the name or IP address of the remote database server
	 * @param sHostname name or IP address of the remote database server
	 */
	protected void setHostname( String sHostname )
	{
		mHostname = sHostname;
	}
	
	/**
	 * Get the TCP port the database is listening on
	 * @return The TCP port the database is listening on
	 */
	public int getPort()
	{
		return mPort;
	}
	
	/**
	 * Set the TCP port the database is expected to listen on
	 * @param iPort the database's TCP port
	 */
	protected void setPort( int iPort )
	{
		mPort = iPort;
	}
	
	/**
	 * Get the user name used for authenticating to the database
	 * @return The user name used for authenticating to the database
	 */
	public String getUsername()
	{
		return mUsername;
	}
	
	/**
	 * Set the user name to be used for authenticating to the database
	 * @param sUsername
	 */
	protected void setUsername( String sUsername )
	{
		mUsername = sUsername;
	}

	@Override
	public Boolean RecordExists(IDeathRecord drTarg) {
		return Match( drTarg.getSSAN()) != null;
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#Match(java.lang.Long)
	 */
	@Override
	public IDeathRecord Match(long SSAN) {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = mConnection.createStatement();
			String query = "select * from RECORDS where " + SSAN_COLUMN + " = " + String.valueOf( SSAN );
			rs = stmt.executeQuery(query);
			boolean bResult = rs.next();
			if ( !bResult )
				return null;
			DeathRecord drNew = new DeathRecord();
			drNew.setSSAN(SSAN);
			drNew.setSurname(rs.getString(LAST_NAME_COLUMN));
			drNew.setGivenName(rs.getString(FIRST_NAME_COLUMN));
			drNew.setMiddleName(rs.getString(MIDDLE_NAME_COLUMN));
			drNew.setBirthDate(new TimeSpan( rs.getDate(EARLIEST_BIRTH_DATE_COLUMN), rs.getDate(LATEST_BIRTH_DATE_COLUMN)));
			drNew.setDeathDate(new TimeSpan( rs.getDate(EARLIEST_DEATH_DATE_COLUMN), rs.getDate(LATEST_DEATH_DATE_COLUMN)));
			// TODO Specify the exception in the interface and implemented classes
//			if ( rs.next() )
//				throw new DuplicateKeyException("More than one record for SSAN " + String.valueOf( SSAN ) );
			return drNew;
		} catch (SQLException e) {
			// TODO Should we just re-throw the SQLException?
			e.printStackTrace();
			return null;
		}
		finally {
			try { rs.close(); } catch (SQLException e) {}
			try { stmt.close(); } catch (SQLException e) {} 
		}
	}

	@Override
	public void AddRecord(IDeathRecord drNew) throws DuplicateKeyException {
		Statement stmt = null;
		
		try {
			stmt = mConnection.createStatement();
			String sql = "INSERT INTO RECORDS VALUES(" +
					String.valueOf( drNew.getSSAN() ) + "," +
					"'" + drNew.getSurname() + "', " +
					"'" + drNew.getGivenName() + "', " +
					"'" + drNew.getMiddleName() + "', " +
					"'" + mDateFormat.format(drNew.getBirthDate().getStart()) + "', " +
					"'" + mDateFormat.format(drNew.getBirthDate().getEnd()) + "', " +
					"'" + mDateFormat.format(drNew.getDeathDate().getStart()) + "', " +
					"'" + mDateFormat.format(drNew.getDeathDate().getEnd()) + "'" +
					")";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try { stmt.close(); } catch (SQLException e) {}
		}
	}
}
