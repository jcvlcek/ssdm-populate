/**
 * 
 */
package socialSecurityDeathIndex;

/**
 * @author vlcek
 *
 */
public interface IDeathRecord {
	long getSSAN();
	
	String getGivenName();
	
	String getMiddleName();
	
	String getSurname();
	
	TimeSpan getBirthDate();
	
	TimeSpan getDeathDate();
}
