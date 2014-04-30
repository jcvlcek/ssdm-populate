/**
 * 
 */
package socialSecurityDeathIndex;

import java.awt.GridLayout;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
	private static List<Class<? extends IDatabaseConnection>> mSponsors = null;
	private static final String NewLine = System.getProperty("line.separator");

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
	 * @return an as-yet unconnected database connection,
	 * or <code>null</code> if <code>sSponsor</code> is not a supported database sponsor
	 */
	public static IDatabaseConnection CreateConnection( String sSponsor )
	{
		// TODO: Use Java's ServiceLoader mechanism instead (?)
		Class<? extends IDatabaseConnection> cTarget = getConnectionClass( sSponsor );
		if ( cTarget != null )
		{
			try {
				Constructor<? extends IDatabaseConnection> ctor = cTarget.getConstructor((Class<?>[])null);
				return (IDatabaseConnection) ctor.newInstance();
			} catch (InstantiationException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cTarget.getName() + "\"" + NewLine + "is an abstract class", "Cannot instance class", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cTarget.getName() + "\"" + NewLine + "does not provide a default constructor", "Cannot instance class", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cTarget.getName() + "\"" + NewLine + "does not provide an accessible default constructor", "Cannot instance class", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cTarget.getName() + "\"" + NewLine + "constructor received an illegal argument", "Cannot instance class", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cTarget.getName() + "\"" + NewLine + "constructor threw an exception:" + NewLine +
						e.getTargetException().getMessage(), "Cannot instance class", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}

		// TODO Else we should actually throw an appropriate exception
		return null;
	}
	
	private static List<Class<? extends IDatabaseConnection>> getSponsors()
	{
		if ( mSponsors == null )
		{
			mSponsors = new ArrayList<Class<? extends IDatabaseConnection>>();
			mSponsors.add( BeanDatabaseConnection.class );
			mSponsors.add( MySqlDatabaseConnection.class );
			mSponsors.add( SqlServerDatabaseConnection.class );
		}
		
		return mSponsors;
	}
	
	private static Class<? extends IDatabaseConnection> getConnectionClass( String sSponsor )
	{
		for ( Class<? extends IDatabaseConnection> cNext : getSponsors() )
		{
			try {
				Method getSponsor = cNext.getMethod("getSponsor", (Class<?>[])null);
				String sNextSponsor = (String) getSponsor.invoke(null, (Object[])null);
				if ( sNextSponsor.equalsIgnoreCase(sSponsor))
					return cNext;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Class \"" + cNext.getName() + "\"" + NewLine + " does not implement required static \"getSponsor\" method", "Internal error", JOptionPane.ERROR_MESSAGE);
			} catch (SecurityException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cNext.getName() + "\"'s" + NewLine + "generated a security violation executing the \"getSponsor\" method" + NewLine + "or accessing the \"DEFAULT_PORT\" field", "Internal error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cNext.getName() + "\"'s" + NewLine + "lacks access to either the \"getSponsor\" method" + NewLine + "or the \"DEFAULT_PORT\" field", "Internal error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cNext.getName() + "\"'s" + NewLine + "\"getSponsor\" method did not match the expected signature", "Internal error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cNext.getName() + "\"'s" + NewLine + "\"getSponsor\" method threw an exception", "Internal error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Gets the default IP port used by a specified database type (sponsor)
	 * @param sSponsor the name of the database type / sponsor
	 * @return the default IP port for the specified database type, if the database is recognized, otherwise -1
	 */
	public static int getDefaultPort( String sSponsor )
	{
		int iPort = -1;
		
		Class<? extends IDatabaseConnection> cTarget = getConnectionClass( sSponsor );
		
		if ( cTarget != null )
		{
			try {
				iPort = (Integer)cTarget.getField("DEFAULT_PORT").getInt(null);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Class \"" + cTarget.getName() + "\"" + NewLine + " does not implement required static \"DEFAULT_PORT\" field", "Internal error", JOptionPane.ERROR_MESSAGE);
			} catch (SecurityException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cTarget.getName() + "\"'s" + NewLine + "generated a security violation executing the \"getSponsor\" method" + NewLine + "or accessing the \"DEFAULT_PORT\" field", "Internal error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cTarget.getName() + "\"'s" + NewLine + "lacks access to either the \"getSponsor\" method" + NewLine + "or the \"DEFAULT_PORT\" field", "Internal error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Class \"" + cTarget.getName() + "\"'s" + NewLine + "\"getSponsor\" method did not match the expected signature", "Internal error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}

		// TODO We should throw an appropriate exception if no class matches
		return iPort;
	}
	
	/**
	 * Prompt the user to supply a username and password for the database
	 * @return the user-supplied password for the database (null if the user cancels the operation)
	 * @param sDefaultUser the default user name for authentication (may be overridden by the user)
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
	 * @throws InstantiationException if the driver class for the chosen database sponsor cannot be loaded
	 * @throws IllegalAccessException if the driver class or its nullary constructor is not accessible
	 * @throws ClassNotFoundException if the driver class for the chosen database sponsor cannot be found
	 * @throws SQLException if an error occurs while connecting to the database
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
