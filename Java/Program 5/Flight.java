/*Flights Object Class
 * 
 * This file is an object class for the flights used in
 * 		the flight database.
 * 
 * File includes following methods:
 * 	- set and get methods for flight numbers,
 * 		flight destinations city and state.
 *	- A toString method to format all three variables
 *		in a neat order.
 *	- A default constructor and constructor to accept
 *		input from program.
 * 
 * Programmer: Lucas Schuetz
 * Written on: 16 February 2014
 * 
 * **changelog** This object has been changed:
 * 
 * Two variables added to handle arrival and
 * 		departure times.
 * Constructors changed accordingly.
 * get and set methods added accordingly.
 * 
 * toString method - changed to allow for times to
 * 		be output.
 * 
 * Programmer: Lucas Schuetz
 * Date:       12 March 2014
 */

public class Flight
{	
	private int flightNum; //Flight number
	private String flightDestC; //Flight city
	private String flightDestS; //Flight state
	private String arriveT; //Arrival time
	private String departT; //Departure time
	
	public Flight()
	{
		this.flightNum   = 1111;
		this.flightDestC = "Chicago";
		this.flightDestS = "IL";
		this.departT     = "0000";
		this.arriveT     = "2400";
	}
	
	public Flight(int flight, String destC, String destS, String departure, String arrival)
	{
		this.flightNum   = flight;
		this.flightDestC = destC;
		this.flightDestS = destS;
		this.departT     = departure;
		this.arriveT     = arrival;
	}
	
	public void setFlight(String thatString)
	{
		int that = Integer.parseInt(thatString);
		this.flightNum = that;
	}
	
	public void setFlightDestC(String that)
	{
		this.flightDestC = that;
	}
	
	public void setFlightDestS(String that)
	{
		this.flightDestS = that;
	}
	
	public void setArrival(String that)
	{
		this.arriveT = that;
	}
	
	public void setDeparture(String that)
	{
		this.departT = that;
	}
	
	public int getFlight()
	{
		return this.flightNum;
	}
	
	public String getFlightDestC()
	{
		return this.flightDestC;
	}
	
	public String getFlightDestS()
	{
		return this.flightDestS;
	}
	
	public String getArrival()
	{
		return this.arriveT;
	}
	
	public String getDeparture()
	{
		return this.departT;
	}
	
	/* toString method
	 * 
	 * This method is given a boolean from
	 * program to format string correctly
	 * whether it is printing in showAll
	 * method or search method.
	 * 
	 * If in search method:
	 * 	timeOutput method called from schuetzP4 class to
	 * 	allow for formatting output correctly with times.
	 * 
	 * aTime = arrival
	 * dTime = departure
	 * eTime = elapsed time.
	 * 
	 * Return string.
	 */
	public String toString(boolean searching)
	{
		String string = "";
		
		if(searching)
		{
			String aTime = schuetzP5.timeOutput(this.arriveT, null, false);
			String dTime = schuetzP5.timeOutput(this.departT, null, false);
			String eTime = schuetzP5.timeOutput(this.departT, this.arriveT, true);
			
			string = "Flight: " + this.flightNum + "\nDestination: " +
						this.flightDestC + ", " + this.flightDestS + 
						"\nDeparts: " + dTime + "\nArrives: " + aTime + 
						"\nTravel Time: " + (eTime.substring(0, 2)) +
						" hours and " + (eTime.substring(3,5)) +
						" minutes.";
		}
		else
		{
			string = "Number: " + this.flightNum + "\tDestination: " +
					this.flightDestC + ", " + this.flightDestS;
		}
		
		return string;
	}
}
