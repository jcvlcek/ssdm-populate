/**
 * Utilities for loading United States Social Security Death Index master files
 * into select databases (e.g. MySQL, SQL Server) and execute queries on the loaded records
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
 * Simple client program for loading the master files and executing queries
 * @author Jim Vlcek
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
	private String mDatabaseType = BeanDatabaseConnection.SPONSOR;
	private static SSDIprogram mDefaultInstance = null;
	
	/**
	 * Default constructor made private; use a named constructor to obtain an instance
	 */
	private SSDIprogram()
	{
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
	 * Connect to a database of the currently-selected type ("Sponsor")
	 */
	public void Connect( )
	{
		mConnection = DatabaseConnection.CreateConnection(getDatabaseType());
		try {
			mConnection.Connect( getDatabasePort() );
			setIsConnected( true );
		} catch (DbConnectionException e1) {
			e1.printStackTrace();
			setIsConnected( false );
		}		
	}
	
	/**
	 * Disconnect the current active connection,
	 * if one exists and is currently in a connected state
	 */
	public void Disconnect()
	{
		if ( mConnection != null )
			mConnection.Disconnect();
		setIsConnected( false );
	}
	
	/**
	 * Loads a Social Security Death Index master file,
	 * optionally adding records, as they are read in, to a connected database
	 */
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
	
	/**
	 * Query the connected database for a record matching
	 * a specified Social Security Account Number
	 * @param lSSAN the Social Security Account Number to query for matching records
	 * @return the matching Death Index record, if one is found, otherwise <code>null</code>
	 */
	public IDeathRecord MatchRecord( long lSSAN )
	{
		return mConnection.Match(lSSAN);
	}
	
	/**
	 * Gets the database connection status of the program
	 * @return <code>true</code> if the program is connected to a database, otherwise <code>false</code>
	 */
	public Boolean getIsConnected()
	{
		return mIsConnected;
	}
	
	/**
	 * Sets the database connection status of the program
	 * @param isConnected the new connection status; <code>true</code> if connection, otherwise <code>false</false>
	 */
	private void setIsConnected( Boolean isConnected )
	{
		Boolean bOldValue = mIsConnected;
		mIsConnected = isConnected;
		firePropertyChange("isConnected", bOldValue, isConnected);
	}
	
	/**
	 * Gets the value of the "add records on read" option
	 * @return <code>true</code> if the user has opted to add Death Index records
	 * to the database as they are read in from a master file
	 */
	public Boolean getAddRecords()
	{
		return mAddRecords;
	}
	
	/**
	 * Sets or clears the "add records on read" option
	 * @param addRecords the desired state of the option: <code>true</code>
	 * to add records to the database as they are read in from a master false,
	 * or <code>false</code> to discard the records after reading them
	 */
	public void setAddRecords( Boolean addRecords )
	{
		Boolean bOldValue = mAddRecords;
		mAddRecords = addRecords;
		firePropertyChange("addRecords", bOldValue, addRecords);
	}
	
	/**
	 * Gets the port number the database server is expected to be listening on
	 * @return the expected port number
	 */
	public int getDatabasePort()
	{
		return mDatabasePort;
	}
	
	/**
	 * Sets the port number the database server is expected to be listening on
	 * @param databasePort the expected port number
	 */
	public void setDatabasePort( int databasePort )
	{
		int iOldValue = mDatabasePort;
		mDatabasePort = databasePort;
		firePropertyChange("databasePort", iOldValue, databasePort);
	}
	
	/**
	 * Gets a {@link java.lang.String} identifier of the database to be connected.
	 * This identifier is associated with the "sponsor" (MySQL, SQL Server, etc.)
	 * of the database, and not with the database server's URL.
	 * @return the identifier {@link java.lang.String} for the database sponsor
	 */
	public String getDatabaseType()
	{
		return mDatabaseType;
	}
	
	/**
	 * Sets the {@link java.lang.String} identifier of the database to be connected.
	 * @param databaseType the identifier {@link java.lang.String} for the database sponsor 
	 */
	public void setDatabaseType( String databaseType )
	{
		String sOldValue = mDatabaseType;
		mDatabaseType = databaseType; 
		firePropertyChange("databaseType", sOldValue, databaseType);
		setDatabasePort( DatabaseConnection.getDefaultPort(databaseType) );
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
