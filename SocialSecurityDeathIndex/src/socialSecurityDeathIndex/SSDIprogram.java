/**
 * 
 */
package socialSecurityDeathIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
			MySqlDatabaseConnection conn = new MySqlDatabaseConnection();
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
