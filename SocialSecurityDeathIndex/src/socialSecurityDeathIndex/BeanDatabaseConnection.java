/**
 * 
 */
package socialSecurityDeathIndex;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Trivial, in-memory "database".<br>
 * Implements {@link socialSecurityDeathIndex.IDatabaseConnection}
 * to enable testing of methods and algorithms without requiring an actual database connection
 * @author Jim Vlcek
 */
public final class BeanDatabaseConnection extends DatabaseConnection {

	/**
	 * Unique {@link java.lang.String} identifier for this database implementation
	 */
	public static final String SPONSOR = "Can of Beans";
	/**
	 * The default IP port this database listens on
	 */
	public static final int DEFAULT_PORT = 0;
	private HashMap<Long, IDeathRecord> mRecords = new HashMap<Long, IDeathRecord>();
	
	/**
	 * Create a database consisting of plain old java objects
	 */
	public BeanDatabaseConnection() {
		// Nothing to do here - yet, at least
	}

	/**
	 * Get the vendor / "sponsor" of the database implementation
	 * @return the name of the vendor / "sponsor" of the database implementation 
	 */
	public static String getSponsor() {
		return SPONSOR;
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#RecordExists(socialSecurityDeathIndex.IDeathRecord)
	 */
	@Override
	public Boolean RecordExists(IDeathRecord drTarg) {
		return mRecords.containsKey(drTarg.getSSAN());
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#Match(long)
	 */
	@Override
	public IDeathRecord Match(long SSAN) {
		return mRecords.get(SSAN);
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.DatabaseConnection#ConnectToDatabase(java.lang.String)
	 */
	@Override
	public void ConnectToDatabase( int iPort )
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		// Nothing to do here - yet, at least
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#AddRecord(socialSecurityDeathIndex.IDeathRecord)
	 */
	@Override
	public void AddRecord(IDeathRecord drSrc) throws DuplicateKeyException {
		long SSAN = drSrc.getSSAN();
		if ( mRecords.containsKey( SSAN))
			throw new DuplicateKeyException( "SSAN \"" + String.valueOf(SSAN) + "\" already exists in the database; can't add a new record" );
		else
			mRecords.put(SSAN, new DeathRecord( drSrc ));
	}

}
