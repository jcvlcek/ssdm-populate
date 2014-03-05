/**
 * 
 */
package socialSecurityDeathIndex;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author Jim
 *
 */
public final class BeanDatabaseConnection extends DatabaseConnection {

	private HashMap<Long, IDeathRecord> mRecords = new HashMap<Long, IDeathRecord>();
	
	/**
	 * 
	 */
	public BeanDatabaseConnection() {
		// TODO Auto-generated constructor stub
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
	public void ConnectToDatabase(String sPassword)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void AddRecord(IDeathRecord drNew) throws DuplicateKeyException {
		// TODO Auto-generated method stub
		if ( mRecords.containsKey( drNew.getSSAN()))
			throw new DuplicateKeyException( "SSAN \"" + String.valueOf(drNew.getSSAN()) + "\" already exists in the database; can't add a new record" );
		else
			// TODO Make a copy of the death record before adding it to the map
			mRecords.put(drNew.getSSAN(), drNew);
	}

}
