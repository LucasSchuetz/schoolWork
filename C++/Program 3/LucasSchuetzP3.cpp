/*
Lucas Schuetz
CS 221 Spring 2014
Assignment 3

This program takes user input to reserve seating on a
plane 15 rows with 8 seats in each row. Users choose
ticket type, row and seat, and if they want to reserve
again. Program checks for out of bounds, input failure,
and if seats were previously reserved.
*/
#include <iostream>
#include <iomanip>
#include <string>
#include <cctype>
using namespace std;

#define ROWS 15 //plane length
#define SEATS 8 //plane width

   //printPlane method"
  //Prints the current seating of the plane.
 //Arguments: plane[][]
//Return type: void
void printPlane(char plane[][SEATS])
{
    cout << right; //for alignment reset
    cout << "|---------------------------------|\n"
         << "| Rows" << setw(7) << "A  "
         << "B  C  D    E  F  G  " << "H|\n"
         << "|---------------------------------|\n";

    for(int i = 0; i < ROWS; i++)
    {
        cout << left << setw(4) << "|Row " << setw(5) << (i + 1);

        for(int j = 0; j < SEATS; j++)
        {
            if(j == 3)
                cout << setw(2) << plane[i][j] << "|| " ;
            else if(j == 7)
                cout << plane[i][j] << "|";
            else
                cout << setw(3) << plane[i][j];
        }
        cout << endl;
    }
    cout << "|'*' = Available  'X' = Reserved"
         << right << setw(4) << "|\n"
         << "|---------------------------------|\n";
}

   //initializePlane method:
  //Initializes the seating chart
 //Arguments: plane[][]
//Return type: void
void initializePlane(char plane[][SEATS])
{
    for(int i = 0; i < ROWS; i++)
        for(int j = 0; j < SEATS; j++)
            plane[i][j] = '*';
}

   //assignSeat method
  //Checks to see if a seat is taken
 //Arguments: plane[][], rowChoice, seatChoice, seatType
//Return type: bool if seat is accepted
bool assignSeat(char plane[][SEATS], int row, char seatChar, int type)
{
    //changes seatChar into integer
    int seat = ((tolower(seatChar) % 48) - 1);

    //checks for valid seat type
    if(type == 1 && (row > 3))
    {
        cin.clear();
        cin.ignore(1000, '\n');
        cout << "That seat is not part of First Class. "
             << "Please try again: ";
    }
    else if(type == 2 && (row < 4 || row > 8))
    {
        cin.clear();
        cin.ignore(1000, '\n');
        cout << "That seat is not part of Econmony Plus. "
             << "Please try again: ";
    }
    else if(type == 3 && (row < 9))
    {
        cin.clear();
        cin.ignore(1000, '\n');
        cout << "That seat is not part of Economy. "
             << "Please try again: ";
    }
    else //if seat type is valid
    {
        row--; //aligns row for input into array (from 1-15 to 0-14)

        //checks for seat already reserved
        if(plane[row][seat] == 'X')
        {
            cin.clear();
            cin.ignore(1000, '\n');
            cout << "Seat " << (row+1) << char(toupper(seatChar))
                 << " is already taken. Please try again: ";
            return false;
        }
        else //if seat is not reserved
            plane[row][seat] = 'X';

            cout << "Seat " << (row+1) << char(toupper(seatChar))
                 << " has been reserved!\n";
        return true;
    }
    return false; //if type is invalid
}

   //seating method:
  //Asks user for seating info. and checks for validity
 //Arguments: plane[][]
//Return type: void
void seating(char plane[][SEATS])
{
    int typeChoice, rowChoice, seatType;
    char seatChoice;
    bool validSeat = false;

    cout << "1) First Class (Rows 1-3)\n"
         << "2) Economy Plus (Rows 4-8)\n"
         << "3) Economy (Rows 9-15)\n"
         << "Enter a ticket type: ";

    while(!(validSeat))
    {
        if(!(cin >> typeChoice))
        {
            cin.clear();
            cin.ignore(1000, '\n');
            cout << "You have chosen incorrectly. "
                 << "Please enter a valid ticket type: ";
        }
        else
        {
            validSeat = true;

            switch(typeChoice)
            {
            case 1:
                cout << "You have chosen First Class.\n"
                     << "Please enter row (1-3) and seat (A-H): ";
                seatType = 1;
                break;
            case 2:
                cout << "You have chosen Economy Plus.\n"
                     << "Please enter row (4-8) and seat (A-H): ";
                seatType = 2;
                break;
            case 3:
                cout << "You have chosen Economy.\n"
                     << "Please enter row (9-15) and seat (A-H): ";
                seatType = 3;
                break;
            default:
                cout << "You have chosen incorrectly. "
                     << "Please enter a valid ticket type: ";
                validSeat = false;
                break;
            }
        }
    }//end while
    validSeat = false; //resets validation for re-use

    while(!(validSeat))
    {
        if(!(cin >> rowChoice))
        {
            cin.clear();
            cin.ignore(1000, '\n');
            cout << "You have chosen an invalid row. "
                 << "Please try again: ";
        }
        else if(!(cin >> seatChoice))
        {
            cin.clear();
            cin.ignore(1000, '\n');
            cout << "You have chosen an invalid seat. "
                 << "Please try again: ";
        }
        else //if row and seat are valid types
        {
            //checks row number in range (1-15)
            if(rowChoice < 1 || rowChoice > 15)
            {
                cin.clear();
                cin.ignore(1000, '\n');
                cout << "You have chosen a row out of range (1-15). "
                     << "Please try again: ";
            }
            //checks seat letter in range (a-h, A-H)
            else if((int(seatChoice) > 0 && int(seatChoice) < 65) ||
                    (int(seatChoice) > 72 && int(seatChoice) < 97) ||
                     int(seatChoice) > 104)
            {
                cin.clear();
                cin.ignore(1000, '\n');
                cout << "You have chosen a seat out of range(a-h, A-H). "
                     << "Please try again: ";
            }
            else //if row and seat are in range
                validSeat = assignSeat(plane, rowChoice, seatChoice, seatType);
        }
    }//end while
}

int main()
{
    char plane[ROWS][SEATS]; //declares 2D array for plane seats
    char again; //used for multiple seat reservations
    bool againCheck = true; //input validation catch

    initializePlane(plane);
    cout << right << setw(31) << "===Rico's Reservations===\n";
    printPlane(plane); //prints initial plane chart
    seating(plane); //Seating section

    //asks user for repeated reservations
    while(againCheck)
    {
        cout << "Would you like to reserve another seat(Y/N): ";

        if(!(cin >> again))
        {
            cin.clear();
            cin.ignore(1000, '\n');
            cout << "Invalid input. Try again(Y/N): ";
        }
        else //if input is correct type
        {
            if(tolower(again) == 'y')
            {
                printPlane(plane);
                seating(plane);
            }
            else if(tolower(again) == 'n')
                againCheck = false;
            else //if input is incorrect letter
            {
                cin.clear();
                cin.ignore(1000, '\n');
                cout << "Invalid input. Try again(Y/N): ";
            }
        }
    }//end while

    //final output of plane seating chart
    cout << right << setw(31) << "===Rico's Reservations===\n";
    printPlane(plane);

    return 0;
}//end main
