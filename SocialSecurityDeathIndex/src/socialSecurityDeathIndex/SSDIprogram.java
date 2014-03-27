/**
 * 
 */
package socialSecurityDeathIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * @author vlcek
 *
 */
public class SSDIprogram implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5655372400582167965L;
	private static final DateFormat mDateFormat = new SimpleDateFormat( "dd MMM yyyy" );
	private static final DateFormat mMonthFormat = new SimpleDateFormat( "MMM yyyy" );
	private IDatabaseConnection mConnection = null;
	private Boolean mIsConnected = false;
	private Boolean mAddRecords = false;
	private int mDatabasePort = 0;
	private static SSDIprogram mDefaultInstance = null;
	
	/**
	 * Default constructor made private; use a named constructor to obtain an instance
	 */
	private SSDIprogram()
	{
		// Nothing to do here, yet...
	}
	
	/**
	 * Named constructor for access to SSDI program singleton instance
	 * @return the single, default instance of the SSDI program
	 */
	public static SSDIprogram Default()
	{
		if ( mDefaultInstance == null )
			mDefaultInstance = new SSDIprogram();
		return mDefaultInstance;
	}
	
	/**
	 * Connect to a database of the specified type
	 * @param sDatabaseType type of database (MySQL, SqlServer, Beans, etc.) to connect 
	 */
	public void Connect( String sDatabaseType )
	{
		if ( sDatabaseType.equalsIgnoreCase("MySQL"))
			mConnection = new MySqlDatabaseConnection();
		else if ( sDatabaseType.equalsIgnoreCase("SQL Server") )
			mConnection = new SqlServerDatabaseConnection();
		else // if ( sDatabaseType.equalsIgnoreCase("Can of Beans"))
			mConnection = new BeanDatabaseConnection();
		// TODO Else we should actually throw an appropriate exception
		try {
			mConnection.Connect( getDatabasePort() );
			setIsConnected( true );
		} catch (DbConnectionException e1) {
			e1.printStackTrace();
			setIsConnected( false );
		}		
	}
	
	public void Disconnect()
	{
		if ( mConnection != null )
			mConnection.Disconnect();
		setIsConnected( false );
	}
	
	public void LoadMasterFile( ) {	
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
					if ( getAddRecords() )
						mConnection.AddRecord(drNext);
				    String sOut = String.valueOf( drNext.getSSAN() ) + " " + drNext.getGivenName() + " " + drNext.getSurname() + ": " + mDateFormat.format( drNext.getBirthDate().getStart() ) + " - ";
				    if ( drNext.getDeathDate().getDurationInDays() <= 1 )
				    	sOut += mDateFormat.format(drNext.getDeathDate().getEnd());
				    else
				    	sOut += mMonthFormat.format(drNext.getDeathDate().getEnd()); 
				    System.out.println( sOut );
				    MainForm.getDefault().setCurrentRecord(drNext);
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
	
	public IDeathRecord MatchRecord( long lSSAN )
	{
		return mConnection.Match(lSSAN);
	}
	
	public Boolean getIsConnected()
	{
		return mIsConnected;
	}
	
	private void setIsConnected( Boolean isConnected )
	{
		Boolean bOldValue = mIsConnected;
		mIsConnected = isConnected;
		firePropertyChange("isConnected", bOldValue, isConnected);
	}
	
	public Boolean getAddRecords()
	{
		return mAddRecords;
	}
	
	public void setAddRecords( Boolean addRecords )
	{
		Boolean bOldValue = mAddRecords;
		mAddRecords = addRecords;
		firePropertyChange("addRecords", bOldValue, addRecords);
	}
	
	public int getDatabasePort()
	{
		return mDatabasePort;
	}
	
	public void setDatabasePort( int databasePort )
	{
		int iOldValue = mDatabasePort;
		mDatabasePort = databasePort;
		firePropertyChange("databasePort", iOldValue, databasePort);
	}
	
	private PropertyChangeSupport changeSupport = 
			new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener 
			listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener 
			listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, 
			Object oldValue,
			Object newValue) {
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

}
