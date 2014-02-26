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
}
