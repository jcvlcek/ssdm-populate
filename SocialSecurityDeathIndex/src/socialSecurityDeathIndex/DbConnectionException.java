/**
 * 
 */
package socialSecurityDeathIndex;

/**
 * Exception thrown on failure of an attempt to create or connect an
 * {@link socialSecurityDeathIndex.IDatabaseConnection}
 * @author Jim Vlcek
 */
public class DbConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 272509893542434911L;

	/**
	 * Creates a database connection exception
	 * with a specified descriptive string
	 * @param message a string message describing the nature and/or cause(s) of the failure
	 */
	public DbConnectionException( String message ) {
		super( message );
	}
	
	/**
	 * Forward a caught exception as a {@link DbConnectionException}
	 * @param message a string describing the caught exception in terms of a database connection
	 * @param innerException the caught-and-forwarded exception
	 */
	public DbConnectionException( String message, Throwable innerException )
	{
		super( message, innerException );
	}
}
