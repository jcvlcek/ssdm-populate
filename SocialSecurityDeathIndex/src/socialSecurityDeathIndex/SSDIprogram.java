/**
 * 
 */
package socialSecurityDeathIndex;

import java.io.BufferedReader;
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
	
	private static int ConnectToDatabase( String sPassword )
	{
		String url = "jdbc:mysql://localhost:3306/", user = "root";
		String dbName = "SSDI";
		String driver = "com.mysql.jdbc.Driver";
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
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter password:");
		JPasswordField pass = new JPasswordField(10);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, "Log in to database",
		                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                         null, options, options[0]);
		if (option == 0) // pressing OK button
		    return new String( pass.getPassword() );
		else
			return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "Hello, world!", "Sample message box", JOptionPane.INFORMATION_MESSAGE);
		
		int iCount = 0;
		try {
			String sPassword = GetPassword( );
			if ( sPassword != null )
				ConnectToDatabase( sPassword );
			String sDeathMasterFile = "/home/vlcek/Downloads/ssdm1.txt";
			final JFileChooser fOpen = new JFileChooser();
			int iResult = fOpen.showOpenDialog(null);
			if ( iResult == JFileChooser.APPROVE_OPTION)
				sDeathMasterFile = fOpen.getSelectedFile().getPath();
			BufferedReader reader = new BufferedReader(new FileReader(sDeathMasterFile));
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
		
		System.out.println( iCount );
	}
}
