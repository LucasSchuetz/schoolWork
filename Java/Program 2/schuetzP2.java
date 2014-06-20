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
	* 
	**end changelog**
*/

import javax.swing.JOptionPane;

public class schuetzP2
{
	public static void main (String [] args)
	{
		int [ ]flightNum = new int [5];
		String [ ] destination = new String [5];

		int lastIndex = loadDatabase ( flightNum, destination );
		
		sortDatabase(flightNum, destination, lastIndex);

		processTransactions ( flightNum, destination, lastIndex );

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
	static int loadDatabase (int [ ] flightNum, String [ ] destination)
	{
		int lastIndex  = 0;
		int i          = 0; //Index to keep track of place
		
		String flightN = JOptionPane.showInputDialog(null, 
				"Please enter flight number for flight "
				+ (i + 1));
			
		while(flightN != null
				&& i < flightNum.length)
		{
			flightNum[i] = Integer.parseInt(flightN);
		
			String flightD = JOptionPane.showInputDialog(null,
					"Please enter destination for flight"
						+ " number " + (flightNum[i]));
			
			flightN = JOptionPane.showInputDialog(null,
					"Please enter flight number for flight "
							+ (i + 2));
			
			destination[i] = flightD;
			
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
	static void sortDatabase(int [] flightN, String [] flightD, int last)
	{
		int tNum     = 0; // temporary value for
						 // sorting numbers
		String tDest = ""; // temp. value for
						  // sorting destinations
		
		for(int i = 0; i < last - 1; i++)
		{
			for(int j = i + 1; j < last; j++)
			{
				if(flightN[i] > flightN[j]) // then swap
				{
					// Sort for flightN
					tNum = flightN[i];
					flightN[i] = flightN[j];
					flightN[j] = tNum;
				
					// Sort for flightD
					tDest = flightD[i];
					flightD[i] = flightD[j];
					flightD[j] = tDest;
				} // end if
			} //end for
		} // end for
	} // end sortDatabase method
	

	/*	processTransactions method
	
		This method is given arrays representing information for flight 
		numbers. It allows the user to either look up a flight (by number),
		list information for all flights, or quit. Processing continues
		until the user chooses "Quit" for a transaction.
	*/
	static void processTransactions(int [ ] flight, String [ ] dest, int last)
	{
		String choice = JOptionPane.showInputDialog(null,
			"Please enter \n'L' to Look up a flight number, " +
			"'S' to Show all flights, or\n'Q' to end");

		while ( !(choice.equalsIgnoreCase("Q") ) )
		{
			if (choice.equalsIgnoreCase("S") )
			{
				showAll( flight, dest, last );
			}
			else if (choice.equalsIgnoreCase("L") )
			{
				lookUpFlight ( flight, dest, last );
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
	static void showAll(int [ ] flight, String [ ] dest, int last)
	{
		String outputAll = "========= WOW Flights =========\n\n"
				+ "Flight\tDestination\n\n";
		
		for(int i = 0; i < last; i++)
		{
			outputAll += flight[i] + "\t"
					+ dest[i] + "\n";
		} //end for
		
		System.out.println(outputAll); //displays all flights
	} //end showAll

	/*	lookUpFlight method

		This method asks the user to enter the desired flight number, and
		then calls a generic search method to locate the index where the
		flight is stored. If the returned index is negative, the flight
		was not	found, and a dialog box will inform the user of this.
		Otherwise a dialog box will show the flight and its destination.
	*/
	static void lookUpFlight (int [ ] flight, String [ ] dest, int last)
	{
		int desiredFlight;		

		String inputValue = JOptionPane.showInputDialog ( null,
			"Which flight do you want to look up? ('cancel' to skip)");
		
		if (inputValue != null)		// look for it
		{
			desiredFlight = Integer.parseInt (inputValue);

			int position = search (flight, desiredFlight, last, dest);

			if (position >= 0)	// success
			{
				JOptionPane.showMessageDialog(null,"Flight: "
					+ desiredFlight + "   Destination: "
					+ dest [position]);
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
	static int search(int [] flight, int desiredFlight,
				int last, String [] destination)
	{
		int pos = -1;
		int s   = 0;             // sets start value
		int e   = last;         // sets end value
		int m   = (s + e) / 2; //sets midpoint value
		
		for(int i = 0; i < last; i++)
		{
			if(desiredFlight != 0 && destination[i] != null)
			{
				while(flight[m] != desiredFlight && s < e)
				{
					if(flight[m] < desiredFlight)
					{
						s = m + 1;
					} // end if
					else
					{
						e = m - 1;
					} // end else
					
					m = (s + e) / 2;
				} // end while
			
				pos = m;
			} // end if
		} // end for
					
		return pos;
	} //end search

	
}	// end of program