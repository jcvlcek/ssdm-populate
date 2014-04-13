/**
 * 
 */
package socialSecurityDeathIndex;

import java.io.Serializable;

/**
 * Interface implemented by any class representing Social Security
 * Death Index master file records.
 * @author Jim Vlcek
 */
public interface IDeathRecord extends Serializable {
	/**
	 * Social Security Account Number (SSAN)
	 * @return the Social Security Account Number as an unformatted {@link java.lang.Long} integer
	 */
	long getSSAN();
	
	/**
	 * Gets the given (first) name of the decedent
	 * @return the decedent's given name, as a {@link java.lang.String}
	 */
	String getGivenName();
	
	/**
	 * Gets the middle name of the decedent
	 * @return the decedent's middle name or initial, if one is known,
	 * as a {@link java.lang.String}
	 */
	String getMiddleName();
	
	/**
	 * Gets the surname (family or last name) of the decedent
	 * @return the decedent's family/last name, as a {@link java.lang.String}
	 */
	String getSurname();
	
	/**
	 * Gets the known range of dates/times within which the decedent's
	 * actual birth occurred
	 * @return a {@link socialSecurityDeathIndex#TimeSpan} within which
	 * the decedent's actual birth is known to have occurred
	 */
	TimeSpan getBirthDate();
	
	/**
	 * Gets the known range of dates/times within which the decedent's
	 * actual death occurred
	 * @return a {@link socialSecurityDeathIndex#TimeSpan} within which
	 * the decedent's actual death is known to have occurred
	 */
	TimeSpan getDeathDate();
}
