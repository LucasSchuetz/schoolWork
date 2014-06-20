/*	Flights Database
	
	This program is the initial version of a database manager for 
	flights by the Wilbur & Orville Wright (WOW) Airline. This initial version
	will store only flight numbers and the destination city, but later
	versions will store more information for each flight.

	This program will first invite the user to enter the flights and 
	destinations for up to fifty flights. It will keep track of the number of
	entries made.

	After initialization of the database, the program will allow users to 
	enter requests to:
		Look up a flight (in which case the program will report the flight
			and destination, or report "no such flight" if it isn't in
			the database);
		Show all Flights (in which case the program will display a fomatted
			list of all flight information on the console);
		Quit.

	Programmer: Rocket J. Squirrel
	Date:		2 January 2014
	
 	**changelog** This program has been edited for the following:
	* 	loadDatabase method - This method has been added, it asks for user
	* 		input of flight numbers and destinations. Returns the total
	* 		number of flights.
	* 	showAll method - This method has been added, it displays all flights
	* 		input previously in one output. Returns nothing.
	* 	search method - This method has been added, it is called to search
	* 		for specific flights, by flight number, requested by user.
	* 		Returns the array position of the requested flight to be 
	* 		output by program in lookUpFlight method.
	* 
	* 	Programmer: Lucas Schuetz
	* 	Date:       14 January 2014
	* -------------------------------------------------------------
	* 	sortDatabase method - This method has been added, it sorts both
	* 		flight and destination arrays based on flight number
	* 		lowest to highest. Returns nothing.
	* 	search method - *UPDATED* This method has been edited, it now
	* 		contains a binary search algorithm, updated from a 
	* 		linear search.
	* 	
	* 	Programmer: Lucas Schuetz
	* 	Date:		3 February 2014
	*  -------------------------------------------------------------
	*  All method headers altered to accept object class Flight.
	*  Flight number and flight destination arrays removed and
	*  		object array flight declared.
	*  search method - Altered to return correct values.
	*  
	*  Programmer: Lucas Schuetz
	*  Date:       17 February 2014
	*  -------------------------------------------------------------
	*  imports added for: BufferedReader, FileReader
	*  loadDatabase method - completely rewritten to accept files
	*  		rather than input from user.
	*  errorReport method - added to catch error with supplied files
	*  		arrival and departure times. Returns boolean.
	*  timeOutput method - added to help format times realistically
	*  		and also helps check for times correctness.
	*  
	*  Programmer: Lucas Schuetz
	*  Date:       12 March 2014
	**end changelog**
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class schuetzP5
{
	public static void main (String [] args)
	{
		Flight [ ] flights = new Flight [50];
		
		int lastIndex = loadDatabase (flights);
		
		if(lastIndex != -1)
		{
			sortDatabase(flights, lastIndex);

			processTransactions (flights, lastIndex);
		}

		System.exit (0); // release swing
	}	// end of main method

	/* loadDatabase method
	 * 
	 * This method is given one empty array, 'flightNum'.
	 * It reads a file supplied and saves flight number,
	 * flight destination (city&state), arrival and 
	 * departure times to flight object class. Exceptions
	 * are caught and program ends with error report. 
	 * If none are found, saves the lastIndex for amount
	 * of spots used. Allows up to 50 flights.
	 * 
	 * Returns total number of flights as 'lastIndex'
	 */
	static int loadDatabase (Flight [] flight)
	{
		int lastIndex = 0;
		String input = "default~empty";
		
		// Initializes array
		for(int j = 0; j < flight.length; j++)
		{
			flight[j] = new Flight();
		}
		
		try
		{
			FileReader read = new FileReader("FlightFile.txt");
			
			BufferedReader buffer = new BufferedReader(read);
			
			input = buffer.readLine();
			
			while(input != null)
			{
				StringTokenizer stok = new StringTokenizer(input, "~");
				
				while(stok.hasMoreTokens())
				{					
					flight[lastIndex].setFlight(stok.nextToken());
					flight[lastIndex].setFlightDestC(stok.nextToken());
					flight[lastIndex].setFlightDestS(stok.nextToken());
					flight[lastIndex].setDeparture(stok.nextToken());
					flight[lastIndex].setArrival(stok.nextToken());
				}
				lastIndex++;
				
				input = buffer.readLine();
			}
			read.close();
		}
		catch(Exception error)
		{
			lastIndex = -1;
		}
		
		boolean error = errorReport(flight, lastIndex);
		
		if(error)
		{
			return -1;
		}
		else
		{
			return lastIndex;
		}
	} //end loadDatabase

	/* errorReport method
	 * 
	 * This method is given flights array and lastIndex used
	 * and checks departure and arrival times for errors with
	 * times given by file. If fail, prints detailed error report.
	 * 
	 * Returns boolean to indicate pass/fail.
	 */
	static boolean errorReport(Flight [] flights, int last)
	{
		String outputError = "******* EXCEPTION REPORT *******\n";
		boolean pass = true;
		int failCount = 0;
		
		for(int i = 0; i < last; i++)
		{
			String aTimeCheck = timeOutput(flights[i].getArrival(),
												"checkMe", true);
			String dTimeCheck = timeOutput(flights[i].getDeparture(),
												"checkMe", true);
			
			if(aTimeCheck != null)
			{
				outputError += "****** Error in input file for " + ""
					+ "arrival at line " + (i+1) + ": " + aTimeCheck + "\n";
				
				failCount++;
				pass = false;
			}
			else if(dTimeCheck != null)
			{
				outputError += "****** Error in input file for " + ""
					+ "departure at line " + (i+1) + ": " + dTimeCheck + "\n";
				
				failCount++;
				pass = false;
			}
			else
			{
				failCount += 0;
			}
		}
		
		if(pass == false)
		{
			System.out.println(outputError + "********** " + 
								failCount + " total errors **********");
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/* sortDatabase Method
	 * 
	 * This method is given two arrays, flightNum and destination filled
	 * out from loadDatabase method. Both arrays are sorted, flight
	 * numbers from smallest to largest and destinations to
	 * correspond with the sorted flight numbers.
	 * 
	 * Returns nothing, ends with arrays being sorted
	 */
	static void sortDatabase(Flight [] flight, int last)
	{
		Flight temp = null;
		
		for(int i = 0; i < last - 1; i++)
		{
			for(int j = i + 1; j < last; j++)
			{
				if(flight[i].getFlight() > flight[j].getFlight()) // then swap
				{
					temp = flight[i];
					flight[i] = flight[j];
					flight[j] = temp;
				}
			}
		}
	} // end sortDatabase method
	

	/*	processTransactions method
	
		This method is given arrays representing information for flight 
		numbers. It allows the user to either look up a flight (by number),
		list information for all flights, or quit. Processing continues
		until the user chooses "Quit" for a transaction.
	*/
	static void processTransactions(Flight [] flight, int last)
	{
		String choice = JOptionPane.showInputDialog(null,
			"Please enter \n'L' to Look up a flight number, " +
			"'S' to Show all flights, or\n'Q' to end");

		while ( !(choice.equalsIgnoreCase("Q") ) )
		{
			if (choice.equalsIgnoreCase("S") )
			{
				showAll( flight, last );
			}
			else if (choice.equalsIgnoreCase("L") )
			{
				lookUpFlight ( flight, last );
			}
			choice = JOptionPane.showInputDialog(null,
				"Please enter \n'L' to Look up a flight number, " +
				"'S' to Show all flights, or\n'Q' to end");
		}  // end while
	}  // end processTransactions method

	/* showAll method
	 * 
	 * This is called only when user wants to see all flight information.
	 * Displays all flight info including flight number, destination,
	 * arrival and departure times.
	 * 
	 * This method is passed flight numbers array, destinations array,
	 * and the lastIndex to be used in outputting final list.
	 */
	static void showAll(Flight [] flight, int last)
	{		
		String outputAll = "========= WOW Flights =========\n\n";
		
		for(int i = 0; i < last; i++)
		{
			String aTime = timeOutput(flight[i].getArrival(), null, false);
			String dTime = timeOutput(flight[i].getDeparture(), null, false);
			
			outputAll += flight[i].toString(false) + "\tDeparts: " + dTime +
					"\tArrives: " + aTime + "\n";
		} //end for
		
		System.out.println(outputAll); //displays all flights
	} //end showAll
	
	/* timeOutput Method
	 * 
	 * This is called to allow for formatting String from flight
	 * class to ampm time from Time class. It is also used by
	 * errorReport class to check for correct times. If checking,
	 * second String given is null.
	 * 
	 * Given String for arrival and depature time and boolean to
	 * control whether looking for travel time or not.
	 * 
	 * Returns a String with following time format:
	 * 		"xx:xx xm" 
	 * 			OR
	 * 		errors/null for errorReport
	 */
	static String timeOutput(String thisTime, String thatTime, boolean elapsed)
	{
		String hr, min;
		int hour, minute;
		
		hr = thisTime.substring(0, 2);
		min = thisTime.substring(2, 4);					
		hour = Integer.parseInt(hr);
		minute = Integer.parseInt(min);
			
		Time times = new Time(hour, minute);
		
		// Chooses whether outputting showAll, search, or errorReport times
		if(!elapsed)
		{
			return(times.ampmTime());
		}
		else if(thatTime.equals("checkMe") && elapsed)
		{
			return(times.checkTime(hour, minute));
		}
		else
		{
			hr = thatTime.substring(0, 2);
			min = thatTime.substring(2, 4);					
			hour = Integer.parseInt(hr);
			minute = Integer.parseInt(min);
			
			Time end = new Time(hour, minute);
			
			Time difference = times.difference(end);

			return(difference.ampmTime());
		}
	}

	/*	lookUpFlight method

		This method asks the user to enter the desired flight number, and
		then calls a generic search method to locate the index where the flight
		is stored. If the returned index is negative, the flight was not
		found, and a dialog box will inform the user of this. Otherwise
		a dialog box will show the flight and its destination.
	*/
	static void lookUpFlight (Flight [ ] flight, int last)
	{
		int desiredFlight;		

		String inputValue = JOptionPane.showInputDialog ( null,
			"Which flight do you want to look up? ('cancel' to skip)");
		
		if (inputValue != null)		// look for it
		{
			desiredFlight = Integer.parseInt (inputValue);

			int position = search (flight, desiredFlight, last);

			if (position >= 0)	// success
			{
				JOptionPane.showMessageDialog(null,
						flight[position].toString(true));
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Flight "
					+ inputValue + " is not listed.");
			}  // end if
		} // end if
	} // end lookUpCode

	/* search method
	 * 
	 * This method looks for the flight number or destination
	 * and returns the position of that flight to be output
	 * to the user.
	 * 
	 * Accepts array flight, int desiredFlight, int last, and array destination
	 * 
	 * Returns the position of the desiredFlight.
	 */
	static int search(Flight [] flight, int desiredFlight,
							int last)
	{
		int pos = -1;
		int s   = 0;             // sets start value
		int e   = last;     // sets end value
		int m   = (s + e) / 2; //sets midpoint value
		boolean exit = false; //allows while to exit
		
		for(int i = 0; i < last; i++)
		{
			if(desiredFlight != 0)
			{
				while(!exit && s <= e)
				{
					if(flight[m].getFlight() < desiredFlight)
					{
						s = m + 1;
					}
					else if(flight[m].getFlight() == desiredFlight)
					{
						pos = m;
						exit = true;
					}
					else
					{
						e = m - 1;
					}
					
					m = (s + e) / 2;
				} // end while
			} // end if
		} // end for
		return pos;
	} //end search
	
}	// end of program
