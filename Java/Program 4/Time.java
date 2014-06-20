/* Time
 * 
 * An object class that deals with time as a whole.
 * Declares integer instance variables hour and minute.
 * 
 * Programmer: Lucas Schuetz
 * Date:   28 February, 2014
 */

public class Time
{
	private int hour, minute;
	
// Default contructor	
	public Time()
	{
		this.hour   = 0;
		this.minute = 0;
	}

// Constructor
	public Time(int hourGiven, int minuteGiven)
	{
		this.hour   = hourGiven;
		this.minute = minuteGiven;
	}

/* toString Method
 * 
 * Method that is given no arguments, 
 * returns a String formatted with objects
 * hour and minute in military time.
 * 
 * Return String.
 */
	public String toString()
	{
		if(this.minute < 10)
		{
			return("Hour: " + this.hour + " Minute: " +
					"0" + this.minute + ".");
		}
		else
		{
			return("Hour: " + this.hour + " Minute: " +
					this.minute + ".");
		}
	}
	
/* setTime Method
 * 
 * Given arguments hourGiven and minuteGiven, allows user
 * to set the time, catches bad input and notifies.
 * 
 * Return null.
 */
	public String setTime(int hourGiven, int minuteGiven)
	{
		if(minuteGiven < 0 || minuteGiven > 59)
		{
			return("Minute given is out of range.");
		}
		else if(hourGiven < 0 || hourGiven > 23)
		{
			return("Hour given is out of range.");
		}
		else
		{
			this.hour   = hourGiven;
			this.minute = minuteGiven;
			
			return(null);
		}
	}
	
/* ampmTime Method
 * 
 * Recieves no arguments, checks object and
 * returns String value that is formatted
 * in AM/PM Time.
 * 
 * Return String.
 */
	public String ampmTime()
	{
		int tempH = this.hour;
		int tempM = this.minute;
		
		if(tempH == 12 && tempM == 0)
		{
			return("Noon.");
		}
		else if(tempH == 0 && tempM == 0)
		{
			return("Midnight.");
		}
		else
		{
			if(tempH == 0)
			{
				if(tempM < 10)
				{
					return(12 + ":" + "0" +
							tempM + " PM.");
				}
				else
				{
					return("12:" + tempM + " AM.");
				}
			}
			else if(tempH > 12)
			{
				tempH -= 12;
				
				if(tempM < 10)
				{
					return(tempH + ":" +
				"0" + tempM + " PM.");
				}
				else
				{
					return(tempH + ":" + tempM +
							" PM.");
				}
			}
			else
			{
				if(tempM < 10)
				{
					return(tempH + ":" +
				"0" + tempM + " AM.");
				}
				else
				{
					return(tempH + ":" + tempM +
							" AM.");
				}				
			}
		}
	}
	
/* milTime Method
 * 
 * Receives no arguments, checks object and
 * return String vlue that is formatted for
 * military (24 hour) time.
 * 
 * Return String.
 */
	public String milTime()
	{
		if(this.hour < 10 || this.minute < 10)
		{
			if(this.hour < 10 && this.minute >= 10)
			{
				return("0" + this.hour +
						this.minute + ".");
			}
			else if(this.minute < 10 && this.hour >= 10)
			{
				return(this.hour + "0" +
						this.minute + ".");
			}
			else
			{
				return("0" + this.hour + "0" +
						this.minute + ".");
			}
		}
		else
		{
			return(this.hour + "" + this.minute);
		}
	}
	
/* increment Method
 * 
 * Given no arguments, adds one to minute time,
 * if minute is 59, set to 0 and adds 1 to hour.
 * 
 * Void return.
 */
	public void increment()
	{
		if(this.minute == 59)
		{
			this.hour++;
			this.minute = 0;
		}
		else
		{
			this.minute++;
		}
	}
	
/* difference Method
 * 
 * Given 'that' Time object, converts time to minutes,
 * checks whether time is later or earlier than 'this'
 * Time object. If later, returns time difference, if
 * earlier, assumes 24-hour difference and returns
 * difference accordingly.
 * 
 * Return Time object.
 */
	public Time difference(Time that)
	{
		int thisTimeMin = (this.hour*60) + this.minute;
		int thatTimeMin = (that.hour*60) + that.minute;
		int diffTimeMin = 0;
		Time diffTime = new Time();
		
		if(thisTimeMin > thatTimeMin)
		{
			if(this.minute >= that.minute)
			{
				diffTime.minute = (that.minute+60) - this.minute;
			}
			else
			{
				diffTime.minute = that.minute - this.minute;
			}
			
			diffTime.hour = (that.hour + 24) - this.hour;
			
			if(diffTime.minute < 0 || diffTime.hour < 0)
			{
				if(diffTime.minute < 0)
				{
					diffTime.minute *= -1;
					return(diffTime);
				}
				else if(diffTime.hour < 0)
				{
					diffTime.hour *= -1;
					return(diffTime);
				}
				else
				{
					return(diffTime);
				}
			}
			else
			{
				return(diffTime);
			}
		}
		else if(thisTimeMin < thatTimeMin)
		{
			if(this.minute >= that.minute)
			{
				diffTime.minute = (that.minute+60) - this.minute;
			}
			else
			{
				diffTime.minute = that.minute - this.minute;
			}
				
			diffTimeMin = thisTimeMin - thatTimeMin;
			
			diffTime.hour = diffTimeMin / 60;
			
			if(diffTime.minute < 0 || diffTime.hour < 0)
			{
				if(diffTime.minute < 0)
				{
					diffTime.minute *= -1;
					return(diffTime);
				}
				else if(diffTime.hour < 0)
				{
					diffTime.hour *= -1;
					return(diffTime);
				}
				else
				{
					return(diffTime);
				}
			}
			else
			{
				return(diffTime);
			}
		}
		else
		{
			diffTime.hour   = 0;
			diffTime.minute = 0;
			return(diffTime);
		}
	}
	
/* later Method
 * 
 * Given 'that' Time object, checks with
 * 'this' Time object to verify whether
 * 'this' is later than 'that'.
 * 
 * Return Boolean.
 */
	public boolean later(Time that)
	{
		int thisTimeMin = (this.hour*60) + this.minute;
		int thatTimeMin = (that.hour*60) + that.minute;
		
		return(thisTimeMin > thatTimeMin);
	}
	
/* equals Method
 * 
 * Given 'that' Time object, check with
 * 'this' Time object to verify whether
 * 'this' is equal to 'that'.
 * 
 * Return Boolean.
 */
	public boolean equals(Time that)
	{
		return((this.hour == that.hour) &&
			   (this.minute == that.minute));
	}
	
/* copy Method
 * 
 * Given no arguments, copies current Time
 * object and copies it to new memory address.
 * 
 * Return Time object.
 */
	public Time copy()
	{
		Time that = new Time(this.hour, this.minute);
		return that;
	}
}