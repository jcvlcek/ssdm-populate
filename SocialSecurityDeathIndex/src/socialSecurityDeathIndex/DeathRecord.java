package socialSecurityDeathIndex;

/**
 * 
 */

/**
 * @author vlcek
 *
 */
public class DeathRecord implements IDeathRecord {
	/*
	 * 
	 */
	// private static DateFormat mDateFormat = new SimpleDateFormat("MMddyyyy HH:mm:ss");
	
	private long mSSAN;
	private String mGivenName;
	private String mMiddleName;
	private String mSurname;
	private TimeSpan mBirthDate;
	private TimeSpan mDeathDate;

	/**
	 * 
	 */
	public DeathRecord( String sRecord ) throws IllegalArgumentException {
		if ( sRecord.length() != 100 )
			throw new IllegalArgumentException( "Death record \"" + sRecord +
					"\" not 100 characters in length");
		
		mSSAN = Long.parseLong(sRecord.substring( 1, 10 ) );
		mSurname = sRecord.substring( 10, 30 );
		mGivenName = sRecord.substring( 34, 49 );
		mMiddleName = sRecord.substring( 49, 64 );
		try {
			String sBirthDate = sRecord.substring( 73, 81 );
			String sDeathDate = sRecord.substring( 65, 73 );
			mBirthDate = new TimeSpan( sBirthDate );
			mDeathDate = new TimeSpan( sDeathDate );
			// System.out.println( sBirthDate + ": " + mBirthDate.getStart().toString() + " - " + mBirthDate.getEnd().toString());
			// System.out.println( sDeathDate + ": " + mDeathDate.getStart().toString() + " - " + mDeathDate.getEnd().toString());			
		} catch ( NumberFormatException ex )
		{
			throw new IllegalArgumentException( "Death record \"" + sRecord +
					"\" contains an illegal date: " + ex.getMessage() );
		}
	}
	
	public long getSSAN() { return mSSAN; }
	
	public String getGivenName() { return mGivenName; }
	
	public String getMiddleName() { return mMiddleName; }
	
	public String getSurname() { return mSurname; }
	
	public TimeSpan getBirthDate() { return mBirthDate; }
	
	public TimeSpan getDeathDate() { return mDeathDate; }
}
