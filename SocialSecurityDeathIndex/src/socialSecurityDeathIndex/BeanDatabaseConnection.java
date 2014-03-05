/**
 * 
 */
package socialSecurityDeathIndex;

import java.sql.SQLException;

/**
 * @author Jim
 *
 */
public final class BeanDatabaseConnection extends DatabaseConnection {

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
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see socialSecurityDeathIndex.IDatabaseConnection#Match(long)
	 */
	@Override
	public IDeathRecord Match(long SSAN) {
		// TODO Auto-generated method stub
		return null;
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

}
