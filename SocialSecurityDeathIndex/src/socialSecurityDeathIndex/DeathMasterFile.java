/**
 * 
 */
package socialSecurityDeathIndex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Jim
 *
 */
public class DeathMasterFile {

	private BufferedReader mReader = null;
	
	/**
	 * @throws FileNotFoundException 
	 * 
	 */
	public DeathMasterFile( String sPath ) throws FileNotFoundException {
		mReader = new BufferedReader(new FileReader(sPath));
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Reads (sequentially) a death record from the Master File
	 * The file pointer is advanced to the subsequent record upon a successful read
	 * @return The next death record in the Master File
	 * @throws IOException
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
	 * Close the death record Master File
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
