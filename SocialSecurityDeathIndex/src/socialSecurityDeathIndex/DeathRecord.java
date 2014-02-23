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
	
	private long mSSAN;
	private String mGivenName;
	private String mMiddleName;
	private String mSurname;
	private TimeSpan mBirthDate;
	private TimeSpan mDeathDate;
	
	// All column indices are zero-referenced
	public static final int ADD_DELETE_CHANGE_COLUMN = 0;
	public static final int SSAN_STARTING_COLUMN = 1;
	public static final int SSAN_ENDING_COLUMN = 9;
	public static final int LAST_NAME_STARTING_COLUMN = 10;
	public static final int LAST_NAME_ENDING_COLUMN = 29;
	public static final int NAME_SUFFIX_STARTING_COLUMN = 30;
	public static final int NAME_SUFFIX_ENDING_COLUMN = 33;
	public static final int FIRST_NAME_STARTING_COLUMN = 34;
	public static final int FIRST_NAME_ENDING_COLUMN = 48;
	public static final int MIDDLE_NAME_STARTING_COLUMN = 49;
	public static final int MIDDLE_NAME_ENDING_COLUMN = 63;
	public static final int VP_CODE_COLUMN = 64;
	public static final int DEATH_DATE_STARTING_COLUMN = 65;
	public static final int DEATH_DATE_ENDING_COLUMN = 72;
	public static final int BIRTH_DATE_STARTING_COLUMN = 73;
	public static final int BIRTH_DATE_ENDING_COLUMN = 80;
	public static final int STATE_CODE_STARTING_COLUMN = 81;
	public static final int STATE_CODE_ENDING_COLUMN = 82;
	public static final int RESIDENCE_ZIP_STARTING_COLUMN = 83;
	public static final int RESIDENCE_ZIP_ENDING_COLUMN = 87;
	public static final int PAYMENT_ZIP_STARTING_COLUMN = 88;
	public static final int PAYMENT_ZIP_ENDING_COLUMN = 92;

	/**
	 * 
	 */
	public DeathRecord( String sRecord ) throws IllegalArgumentException {
		if ( sRecord.length() != 100 )
			throw new IllegalArgumentException( "Death record \"" + sRecord +
					"\" not 100 characters in length");
		
		mSSAN = Long.parseLong(sRecord.substring( SSAN_STARTING_COLUMN, SSAN_ENDING_COLUMN+1 ) );
		mSurname = sRecord.substring( LAST_NAME_STARTING_COLUMN, LAST_NAME_ENDING_COLUMN+1 );
		mGivenName = sRecord.substring( FIRST_NAME_STARTING_COLUMN, FIRST_NAME_ENDING_COLUMN+1 );
		mMiddleName = sRecord.substring( MIDDLE_NAME_STARTING_COLUMN, MIDDLE_NAME_ENDING_COLUMN+1 );
		try {
			String sBirthDate = sRecord.substring( BIRTH_DATE_STARTING_COLUMN, BIRTH_DATE_ENDING_COLUMN+1 );
			String sDeathDate = sRecord.substring( DEATH_DATE_STARTING_COLUMN, DEATH_DATE_ENDING_COLUMN+1 );
			mBirthDate = new TimeSpan( sBirthDate );
			mDeathDate = new TimeSpan( sDeathDate );
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
