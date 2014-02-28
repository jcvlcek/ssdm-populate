/**
 * 
 */
package socialSecurityDeathIndex;

import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author vlcek
 *
 */
public abstract class DatabaseConnection implements IDatabaseConnection {

	public static final String DEFAULT_DATABASE_HOST = "localhost";
	public static final String DEFAULT_DATABASE_NAME = "SSDI";
	
	private static final String LINE_SEPARATOR = System.getProperty( "line.separator");
	
	private String mDatabaseName;

	/**
	 * Default constructor for the DatabaseConnection class
	 */
	public DatabaseConnection() {
		mDatabaseName = DEFAULT_DATABASE_NAME;
	}
	
	/**
	 * Prompt the user to supply a username and password for the database
	 * @return the user-supplied password for the database (null if the user cancels the operation)
	 */
	public static String GetPassword()
	{
		//Using a JPanel as the message for the JOptionPane
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new GridLayout(2,2));

		//Labels for the textfield components        
		JLabel lblUsername = new JLabel("Username:");
		JLabel lblPassword = new JLabel("Password:");

		JTextField username = new JTextField();
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
		    return new String( fldPassword.getPassword() );
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#Connect()
	 */
	@Override
	public void Connect() throws DbConnectionException {
		try {
			String sPassword = GetPassword();
			if ( sPassword != null )
				ConnectToDatabase( sPassword );
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
	public void Disconnect() {
		// TODO Auto-generated method stub

	}
	
	public abstract void ConnectToDatabase( String sPassword ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	
	public String getDatabaseName()
	{
		return mDatabaseName;
	}

}
