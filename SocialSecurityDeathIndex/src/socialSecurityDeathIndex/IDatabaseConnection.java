/**
 * 
 */
package socialSecurityDeathIndex;

/**
 * @author Jim
 *
 */
public interface IDatabaseConnection {
	/**
	 * Connect to a database, using default connection parameters
	 * @throws DbConnectionException
	 */
	void Connect() throws DbConnectionException;
	
	/**
	 * Connect to the default database host at a specified port
	 * @param iPort the TCP port to connect
	 * @throws DbConnectionException
	 */
	void Connect( int iPort ) throws DbConnectionException;
	
	/**
	 * Connect to a specific database on a remote host
	 * @param Hostname the remote database host (server)
	 * @param database the name of the database on the remote host (server)
	 * @throws DbConnectionException
	 */
	void Connect( String Hostname, String database ) throws DbConnectionException;
	
	/**
	 * Close the connection to the database
	 */
	void Disconnect();
	
	/**
	 * Get the name of the database on the remote host
	 * @return the name of the database
	 */
	String getDatabaseName();
	
	/**
	 * Get the name or IP address of the remote database host
	 * @return a valid hostname or IP address
	 */
	String getHostname();
	
	/**
	 * Tests whether a specified record already exists in the database
	 * @param drTarg The death record whose existence is to be tested
	 * @return true if the database contains a death record
	 * matching the SSAN of drTarg, otherwise false
	 */
	Boolean RecordExists( IDeathRecord drTarg );
	
	/**
	 * Queries the database for a specified
	 * social security account number
	 * @param SSAN The SSAN to test for
	 * @return the matching death record, if one exists, otherwise null
	 */
	IDeathRecord Match( long SSAN );
	
	/**
	 * Add a new record to the database
	 * @param drNew the new record to add
	 * @throws DuplicateKeyException if a record exists with the same SSAN as drNew
	 */
	void AddRecord( IDeathRecord drNew ) throws DuplicateKeyException;
}
