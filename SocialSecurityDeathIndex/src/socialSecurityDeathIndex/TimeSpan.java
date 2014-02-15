/**
 * 
 */
package socialSecurityDeathIndex;

import java.util.Calendar;
import java.util.Date;

/**
 * @author vlcek
 *
 */
public class TimeSpan {

	private Date mStart, mEnd;
	private long mDurationInSeconds;
	public static final int SECONDS_PER_DAY = 86400;
	
	private int LastDayOfMonth( int iYear, int iMonth )
	{
		switch ( iMonth )
		{
		case Calendar.JANUARY:
		case Calendar.MARCH:
		case Calendar.MAY:
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.OCTOBER:
		case Calendar.DECEMBER:
			return 31;
		case Calendar.APRIL:
		case Calendar.JUNE:
		case Calendar.SEPTEMBER:
		case Calendar.NOVEMBER:
			return 30;
		case Calendar.FEBRUARY:
			if ( ( iYear % 4 ) != 0 )
				return 28;
			else {
				if ( ( iYear % 100 ) != 0 )
					return 29;
				else if ( ( iYear % 400 ) == 0 )
					return 29;
				else
					return 28;
			}
		default:
			return 30;
		}
	}
	/**
	 * 
	 */
	public TimeSpan( String sSpan ) throws NumberFormatException {
		String sMonth = sSpan.substring(0, 2);
		String sDay   = sSpan.substring(2, 4);
		String sYear  = sSpan.substring(4, 8);
		
		int iMonth = Integer.parseInt(sMonth) - 1;
		int iDay   = Integer.parseInt(sDay);
		int iYear  = Integer.parseInt(sYear);
		
		Calendar cal = Calendar.getInstance();
		if ( iDay == 0 ) {
			cal.set(iYear, iMonth, 1, 0, 0, 0);
			mStart = cal.getTime();
			int iEndOfMonth = LastDayOfMonth( iYear, iMonth );
			cal.set(iYear, iMonth, iEndOfMonth, 23, 59, 59);
			mEnd = cal.getTime();
			mDurationInSeconds = SECONDS_PER_DAY * iEndOfMonth;
		}
		else {
			cal.set(iYear, iMonth, iDay, 0, 0, 0);
			mStart = cal.getTime();
			cal.set(iYear, iMonth, iDay, 23, 59, 59);
			mEnd = cal.getTime();
			mDurationInSeconds = SECONDS_PER_DAY;
		}
	}
	
	public TimeSpan() {
		Calendar cal = Calendar.getInstance();
		mStart = mEnd = cal.getTime();
		mDurationInSeconds = 0;
	}

	public Date getStart() { return mStart; }
	
	public Date getEnd() { return mEnd; }
	
	public long getDurationInSeconds() { return mDurationInSeconds; }
	
	public long getDurationInDays() { return mDurationInSeconds / SECONDS_PER_DAY; }
}
