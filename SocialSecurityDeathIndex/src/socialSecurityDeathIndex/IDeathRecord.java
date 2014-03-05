/**
 * 
 */
package socialSecurityDeathIndex;

import java.io.Serializable;

/**
 * @author vlcek
 *
 */
public interface IDeathRecord extends Serializable {
	long getSSAN();
	
	String getGivenName();
	
	String getMiddleName();
	
	String getSurname();
	
	TimeSpan getBirthDate();
	
	TimeSpan getDeathDate();
}
