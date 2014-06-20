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
 */

public class Flight
{	
	private int flightNum; //Flight number
	private String flightDestC; //Flight city
	private String flightDestS; //Flight state
	
	public Flight()
	{
		this.flightNum   = 1111;
		this.flightDestC = "Chicago";
		this.flightDestS = "IL";
	}
	
	public Flight(int flight, String destC, String destS)
	{
		this.flightNum   = flight;
		this.flightDestC = destC;
		this.flightDestS = destS;
	}
	
	public void setFlight(int that)
	{
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
	
	/* toString method
	 * 
	 * This method is given a boolean from
	 * program to format string correctly
	 * whether it is printing in showAll
	 * method or search method.
	 */
	public String toString(boolean searching)
	{
		String string = "";
		
		if(searching)
		{
			string = "Number: " + this.flightNum + " Destination: " +
					this.flightDestC + ", " + this.flightDestS;
		}
		else
		{
			string = "Number: " + this.flightNum + "\tDestination: " +
					this.flightDestC + ", " + this.flightDestS;
		}
		
		return string;
	}
}
