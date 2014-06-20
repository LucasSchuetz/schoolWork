/*
Lucas Schuetz
CS 221 Spring 2014
Assignment 1

This program requests users name and gross salary. Taxes are deducted from
the gross salary and the program outputs the users net pay.
*/

#include <iostream>
#include <string>
#include <iomanip>

using namespace std;

int main()
{
    //Variable Declarations
    float taxFedInc  = .132;
    float taxState   = .0525;
    float taxSocial  = .039;
    float taxMed     = .0163;
    float retirement = .0472;
    float healthIns  = 42.00;
    float lifeIns    = 3.17;

    string name;
    float grossSalary, netPay;

    //Prompt
    cout << "Enter employee's full name: ";
    getline(cin, name);
    cout << "Enter gross salary for " << name << ": ";
    cin  >> grossSalary;
    cout << endl;
    netPay = grossSalary;

    //Deductions
    netPay -= (grossSalary * taxFedInc);
    taxFedInc = (grossSalary * taxFedInc);
    netPay -= (grossSalary * taxState);
    taxState = (grossSalary * taxState);
    netPay -= (grossSalary * taxSocial);
    taxSocial = (grossSalary * taxSocial);
    netPay -= (grossSalary * taxMed);
    taxMed = (grossSalary * taxMed);
    netPay -= (grossSalary * retirement);
    retirement = (grossSalary * retirement);
    netPay -= (healthIns + lifeIns);

    //Formatted Output
    cout << fixed << setprecision(2) << setfill('*')
         << "Paycheck for " << name << endl
         << "Gross Amount: ........... $" << setw(7) << grossSalary
         << right << endl << endl << "Deductions\n"
         << "Federal Tax: ............ $" << setw(7) << taxFedInc << endl
         << "State Tax: .............. $" << setw(7) << taxState << endl
         << "Social Security Tax: .... $" << setw(7) << taxSocial << endl
         << "Medicare Tax: ........... $" << setw(7) << taxMed << endl
         << "Retirement Plan: ........ $" << setw(7) << retirement << endl
         << "Health Insurance: ....... $" << setw(7) << healthIns << endl
         << "Life Insurance: ......... $" << setw(7) << lifeIns << endl << endl
         << "Net Pay: ................ $" << setw(7) << netPay << endl;

    return 0;
}
