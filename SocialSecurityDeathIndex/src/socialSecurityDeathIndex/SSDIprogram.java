/**
 * 
 */
package socialSecurityDeathIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;

/**
 * @author vlcek
 *
 */
public class SSDIprogram {
	
	private static final DateFormat mDateFormat = new SimpleDateFormat( "dd MMM yyyy" );
	private static final DateFormat mMonthFormat = new SimpleDateFormat( "MMM yyyy" );
	private static IDatabaseConnection mConnection = null;
	
	/**
	 * @param args
	 */
	
	public static void Connect( String sDatabaseType )
	{
		if ( sDatabaseType.equalsIgnoreCase("MySQL"))
			mConnection = new MySqlDatabaseConnection();
		else if ( sDatabaseType.equalsIgnoreCase("SQLServer") )
			mConnection = new SqlServerDatabaseConnection();
		else // if ( sDatabaseType.equalsIgnoreCase("Can of Beans"))
			mConnection = new BeanDatabaseConnection();
		// TODO Else we should actually throw an appropriate exception
		try {
			mConnection.Connect();
		} catch (DbConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	
	public static void LoadMasterFile( Boolean bAddToDatabase ) {	
		int iCount = 0;
		try {
			File fRootPath = new File( System.getProperty("user.dir") );
			File fTestDataPath = new File( fRootPath, "src/test/resources" );
			File fDeathMasterFile = new File( fTestDataPath, "ssdm_sample.txt" );
			final JFileChooser fOpen = new JFileChooser( fTestDataPath );
			int iResult = fOpen.showOpenDialog(null);
			if ( iResult == JFileChooser.APPROVE_OPTION)
				fDeathMasterFile = fOpen.getSelectedFile();
			else
				return;
			DeathMasterFile fMaster = new DeathMasterFile( fDeathMasterFile.getPath() );
			MainForm.getDefault().setMasterFile(fDeathMasterFile.getPath());
			try {
				IDeathRecord drNext;
				while (( drNext = fMaster.getNext()) != null) {
					if ( bAddToDatabase )
						mConnection.AddRecord(drNext);
				    String sOut = String.valueOf( drNext.getSSAN() ) + " " + drNext.getGivenName() + " " + drNext.getSurname() + ": " + mDateFormat.format( drNext.getBirthDate().getStart() ) + " - ";
				    if ( drNext.getDeathDate().getDurationInDays() <= 1 )
				    	sOut += mDateFormat.format(drNext.getDeathDate().getEnd());
				    else
				    	sOut += mMonthFormat.format(drNext.getDeathDate().getEnd()); 
				    System.out.println( sOut );
				    MainForm.getDefault().setCurrentRecord(sOut);
				    ++iCount;
				    if ( ( iCount % 100000 ) == 0 )
				    	System.out.println( iCount );
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (DuplicateKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				fMaster.Close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, String.valueOf(iCount) + " total records read", "SSDM Import completed", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static IDeathRecord MatchRecord( long lSSAN )
	{
		return mConnection.Match(lSSAN);
	}
}
