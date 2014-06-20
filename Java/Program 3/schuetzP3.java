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
	**end changelog**
*/

import javax.swing.JOptionPane;

public class schuetzP3
{
	public static void main (String [] args)
	{
		Flight [ ] flights = new Flight [5];
		
		int lastIndex = loadDatabase (flights);
		
		sortDatabase(flights, lastIndex);

		processTransactions ( flights, lastIndex );

		System.exit (0); // release swing
	}	// end of main method

	/* loadDatabase method
	 * 
	 * This method is given two empty arrays, 'flightNum' and 'destination'.
	 * It prompts the user to enter flight information, both flight numbers
	 * and flight destinations, to be output to users when asked. Saves the
	 * lastIndex for amount of spots used.
	 * 
	 * Returns total number of flights as 'lastIndex'
	 */
	static int loadDatabase (Flight [ ] flight)
	{
		int lastIndex      = 0;
		int i              = 0; //Index to keep track of place
		String flightDestC = "";
		String flightDestS = "";
		
		// Gives default values to array
		for(int j = 0; j < flight.length; j++)
		{
		flight[j] = new Flight();
		}
		
		String flightN = JOptionPane.showInputDialog(null, 
				"Please enter flight number for flight "
				+ (i + 1));
			
		while(flightN != null && i < flight.length)
		{
			int flightNum = Integer.parseInt(flightN);
			flight[i].setFlight(flightNum);
		
			flightDestC = JOptionPane.showInputDialog(null,
					"Please enter city for flight"
						+ " number " + (flight[i].getFlight()));
			
			flightDestS = JOptionPane.showInputDialog(null,
					"Please enter state for flight"
						+ " number " + (flight[i].getFlight()));
			
			flight[i].setFlightDestC(flightDestC);
			flight[i].setFlightDestS(flightDestS);
			
			if(i < flight.length - 1)
			{
			flightN = JOptionPane.showInputDialog(null,
					"Please enter flight number for flight "
							+ (i + 2));
			}
			
			i++;
		} //end while
		
		lastIndex = i;
		
		return lastIndex;
	} //end loadDatabase

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
				if(flight[i].getFlight() > flight[j].getFlight())//then swap
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
	static void processTransactions(Flight [ ] flight, int last)
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
	 * Displays all flight info including flight number and destination.
	 * 
	 * This method is passed flight numbers array, destinations array,
	 * and the lastIndex to be used in outputting final list.
	 */
	static void showAll(Flight [ ] flight, int last)
	{
		String outputAll = "========= WOW Flights =========\n\n";
		
		for(int i = 0; i < last; i++)
		{
			outputAll += flight[i].toString(false) + "\n";
		} //end for
		
		System.out.println(outputAll); //displays all flights
	} //end showAll

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
			if(desiredFlight != 0 && desiredFlight <= flight.length+1)
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