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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2378917208976269195L;
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
	 * Default constructor
	 */
	public DeathRecord()
	{
		mSSAN = 0;
		mGivenName = "Unknown";
		mMiddleName = "";
		mSurname = "Unknown";
		mBirthDate = new TimeSpan();
		mDeathDate = new TimeSpan();
	}
	
	/**
	 * Create a copy of an existing death record
	 * @param drSrc the death record to copy
	 */
	public DeathRecord( IDeathRecord drSrc )
	{
		setSSAN( drSrc.getSSAN());
		setGivenName( drSrc.getGivenName());
		setMiddleName( drSrc.getMiddleName());
		setSurname( drSrc.getSurname());
		setBirthDate( drSrc.getBirthDate());
		setDeathDate( drSrc.getDeathDate());
	}

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
	
	public void setSSAN( long SSAN ) { mSSAN = SSAN; }
	
	public String getGivenName() { return mGivenName; }
	
	public void setGivenName( String GivenName ) { mGivenName = GivenName; }
	
	public String getMiddleName() { return mMiddleName; }
	
	public void setMiddleName( String MiddleName ) { mMiddleName = MiddleName; }
	
	public String getSurname() { return mSurname; }
	
	public void setSurname( String Surname ) { mSurname = Surname; }
	
	public TimeSpan getBirthDate() { return mBirthDate; }
	
	public void setBirthDate( TimeSpan DeathDate ) { mBirthDate = DeathDate; }

	public TimeSpan getDeathDate() { return mDeathDate; }
	
	public void setDeathDate( TimeSpan DeathDate ) { mDeathDate = DeathDate; }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mBirthDate == null) ? 0 : mBirthDate.hashCode());
		result = prime * result
				+ ((mDeathDate == null) ? 0 : mDeathDate.hashCode());
		result = prime * result
				+ ((mGivenName == null) ? 0 : mGivenName.hashCode());
		result = prime * result
				+ ((mMiddleName == null) ? 0 : mMiddleName.hashCode());
		result = prime * result + (int) (mSSAN ^ (mSSAN >>> 32));
		result = prime * result
				+ ((mSurname == null) ? 0 : mSurname.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeathRecord other = (DeathRecord) obj;
		if (mBirthDate == null) {
			if (other.mBirthDate != null)
				return false;
		} else if (!mBirthDate.equals(other.mBirthDate))
			return false;
		if (mDeathDate == null) {
			if (other.mDeathDate != null)
				return false;
		} else if (!mDeathDate.equals(other.mDeathDate))
			return false;
		if (mGivenName == null) {
			if (other.mGivenName != null)
				return false;
		} else if (!mGivenName.equals(other.mGivenName))
			return false;
		if (mMiddleName == null) {
			if (other.mMiddleName != null)
				return false;
		} else if (!mMiddleName.equals(other.mMiddleName))
			return false;
		if (mSSAN != other.mSSAN)
			return false;
		if (mSurname == null) {
			if (other.mSurname != null)
				return false;
		} else if (!mSurname.equals(other.mSurname))
			return false;
		return true;
	}
}
