/**
 * 
 */
package socialSecurityDeathIndex;

/**
 * Exception thrown on an attempt to add a record, with a
 * unique key (the Social Security Account Number) equal
 * to the key of an existing record, to a
 * Social Security Death Index database
 * @author Jim Vlcek
 */
public class DuplicateKeyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7635668495979131324L;

	/**
	 * Constructs a <code>DuplicateKeyException</code> with default parameters
	 */
	public DuplicateKeyException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructs  <code>DuplicateKeyException</code> with a specified error message
	 * @param arg0 the error message to associate with the exception
	 */
	public DuplicateKeyException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
