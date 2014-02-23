/**
 * 
 */
package socialSecurityDeathIndex;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;

/**
 * @author vlcek
 *
 */
public class SSDIprogram {
	
	private static DateFormat mDateFormat = new SimpleDateFormat( "dd MMM yyyy" );
	private static DateFormat mMonthFormat = new SimpleDateFormat( "MMM yyyy" );
	
	public static final String DEFAULT_DATABASE_HOST = "localhost";
	public static final int DEFAULT_PORT = 3306;
	public static final String DEFAULT_DATABASE_USER = "root";
	public static final String DEFAULT_DATABASE_NAME = "SSDI";
	public static final String DEFAULT_URL_BASE = "jdbc:mysql://";
	public static final String DEFAULT_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	
	private static int ConnectToDatabase( String sPassword )
	{
		String url = "jdbc:mysql://localhost:3306/";
		url = DEFAULT_URL_BASE + DEFAULT_DATABASE_HOST + ":" + String.valueOf( DEFAULT_PORT ) + "/";
		String user = DEFAULT_DATABASE_USER;
		String dbName = DEFAULT_DATABASE_NAME;
		String driver = DEFAULT_DATABASE_DRIVER;
		try {
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url + dbName, user, sPassword);
			conn.close();
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	
	private static String GetPassword()
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		int iCount = 0;
		try {
			String sPassword = GetPassword( );
			if ( sPassword != null )
				ConnectToDatabase( sPassword );
			File fRootPath = new File( System.getProperty("user.dir") );
			File fTestDataPath = new File( fRootPath, "src/test/resources" );
			File fDeathMasterFile = new File( fTestDataPath, "ssdm_sample.txt" );
			final JFileChooser fOpen = new JFileChooser( fTestDataPath );
			int iResult = fOpen.showOpenDialog(null);
			if ( iResult == JFileChooser.APPROVE_OPTION)
				fDeathMasterFile = fOpen.getSelectedFile();
			BufferedReader reader = new BufferedReader(new FileReader(fDeathMasterFile));
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
				    DeathRecord drNew = new DeathRecord( line );
				    String sOut = drNew.getGivenName() + " " + drNew.getSurname() + ": " + mDateFormat.format( drNew.getBirthDate().getStart() ) + " - ";
				    if ( drNew.getDeathDate().getDurationInDays() <= 1 )
				    	sOut += mDateFormat.format(drNew.getDeathDate().getEnd());
				    else
				    	sOut += mMonthFormat.format(drNew.getDeathDate().getEnd()); 
				    System.out.println( sOut );
				    ++iCount;
				    if ( ( iCount % 100000 ) == 0 )
				    	System.out.println( iCount );
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, String.valueOf(iCount) + " total records read", "SSDM Import completed", JOptionPane.INFORMATION_MESSAGE);
	}
}
