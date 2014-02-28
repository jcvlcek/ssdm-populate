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

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		int iCount = 0;
		try {
			IDatabaseConnection conn = 
					// new MySqlDatabaseConnection();
					new SqlServerDatabaseConnection();
			try {
				conn.Connect();
			} catch (DbConnectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			File fRootPath = new File( System.getProperty("user.dir") );
			File fTestDataPath = new File( fRootPath, "src/test/resources" );
			File fDeathMasterFile = new File( fTestDataPath, "ssdm_sample.txt" );
			final JFileChooser fOpen = new JFileChooser( fTestDataPath );
			int iResult = fOpen.showOpenDialog(null);
			if ( iResult == JFileChooser.APPROVE_OPTION)
				fDeathMasterFile = fOpen.getSelectedFile();
			DeathMasterFile fMaster = new DeathMasterFile( fDeathMasterFile.getPath() );
			try {
				IDeathRecord drNext;
				while (( drNext = fMaster.getNext()) != null) {
				    String sOut = drNext.getGivenName() + " " + drNext.getSurname() + ": " + mDateFormat.format( drNext.getBirthDate().getStart() ) + " - ";
				    if ( drNext.getDeathDate().getDurationInDays() <= 1 )
				    	sOut += mDateFormat.format(drNext.getDeathDate().getEnd());
				    else
				    	sOut += mMonthFormat.format(drNext.getDeathDate().getEnd()); 
				    System.out.println( sOut );
				    ++iCount;
				    if ( ( iCount % 100000 ) == 0 )
				    	System.out.println( iCount );
				}
			} catch (IllegalArgumentException e) {
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
}
