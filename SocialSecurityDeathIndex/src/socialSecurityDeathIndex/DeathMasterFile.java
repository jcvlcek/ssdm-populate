/**
 * 
 */
package socialSecurityDeathIndex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A United States Social Security Death Index master file.<br>
 * @author Jim Vlcek
 */
public class DeathMasterFile {

	private BufferedReader mReader = null;
	
	/**
	 * Creates a Social Security Death Index master file from a file on disk
	 * @param sPath the path of the file on disk to open as a Death Index master file
	 * @throws FileNotFoundException if the specified path does not exist, is a directory
	 * rather than file, or cannot be opened for reading for another reason
	 */
	public DeathMasterFile( String sPath ) throws FileNotFoundException {
		mReader = new BufferedReader(new FileReader(sPath));
	}
	
	/**
	 * Reads (sequentially) a death record from the Master File.<br>
	 * The file pointer is advanced to the subsequent record upon a successful read
	 * @return The next death record in the Master File
	 * @throws IOException if an I/O error occurs while reading the underlying disk file
	 */
	public IDeathRecord getNext() throws IOException
	{
		String sLine;
		
		if ( ( sLine = mReader.readLine()) == null )
			return null;
		else
			return new DeathRecord( sLine );
	}

	/**
	 * Close the Death Index master file
	 */
	public void Close()
	{
		if (mReader != null )
		{
			try {
				mReader.close();
			} catch (IOException e) {
				// TODO Do we care if the close throws an exception?
				e.printStackTrace();
			}
		}
	}
}
