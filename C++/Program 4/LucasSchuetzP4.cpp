/*
Programmer: Lucas Schuetz
Date: June 2 2014
Description: Reads a given file and processes (grades and inputs)
for prompt. User may write the grades to a new file, view a questions
individual stats, or exit.
*/
#include <iostream>
#include <fstream>
#include <string>
#include <iomanip>
#include <cmath>
#include <sstream>

using namespace std;

/* prompt Method
Asks for user input whether to...
1) Write test results to a file
2) View individual stats
3) Exit

Arguments: nothing
Returns  : input
*/
int prompt()
{
    int input;
    bool badInput = true;

    do
    {
        cout << "Would you like to...\n"
             << "1) Write test results to a file\n"
             << "2) View individual question statistics\n"
             << "3) Exit\n"
             << "Enter Choice:";
        if(!(cin >> input))
        {
            cin.clear();
            cin.ignore(100, '\n');
            cout << "Invalid Input, try again.\n\n";
        }
        else
        {
            if(input < 0 || input > 3)
            {
                cin.clear();
                cin.ignore(100, '\n');
                cout << "Invalid Input, try again.\n\n";
            }
            else //input accepted
                badInput = false;
        }
    }while(badInput);
    cout << endl << endl;

    return input;
}

/* stats Method
Grades individual questions, asks for which would
like to be displayed, outputs on user command.

Arguments: int students, string **grades, string answers, int questions
Returns  : void
*/
void stats(int students, string **grades, string answers, int questions)
{
    int choice, ansCorrect = 0;//sets only ansCorrect to '0'
    int ansA = 0;    // Declares
    int ansB = 0;   // zero's for student
    int ansC = 0;  // answers on each
    int ansD = 0; // question
    int ansS = 0;//
    char qAnswer, sAnswer, charChoice; //question, student answers, and char input
    bool badInput = true;

    cout << endl << "Which question would you like to view (1-"
         << questions << "):"; //sad face? Na...
    do
    {
        if(!(cin >> choice))
        {
            cin.clear();
            cin.ignore(100, '\n');
            cout << "Bad input please enter a correct value:";
        }
        else //if input type correct
        {
            if(choice < 1 || choice > questions)
            {
                cin.clear();
                cin.ignore(100, '\n');
                cout << "That question does not exist, please try again:";
            }
            else //if range accepted
            {
                badInput = false;
                choice--; //for use in arrays
                qAnswer = answers[choice];

                for(int i = 0; i < students; i++)
                {
                    sAnswer = grades[i][1][choice];

                    if(qAnswer == sAnswer)
                        ansCorrect++;

                    switch(sAnswer)
                    {
                    case 'A':
                        ansA++;
                        break;
                    case 'B':
                        ansB++;
                        break;
                    case 'C':
                        ansC++;
                        break;
                    case 'D':
                        ansD++;
                        break;
                    case ' ':
                        ansS++;
                    }
                }

                //Math section
                float fAnsA = (float(ansA) / students) * 100;
                float fAnsB = (float(ansB) / students) * 100;
                float fAnsC = (float(ansC) / students) * 100;
                float fAnsD = (float(ansD) / students) * 100;
                float fAnsS = (float(ansS) / students) * 100;
                float fAnsCorrect = (float(ansCorrect) / students) * 100;
                //end math section

                cout << "\nStatistics for Question " << (choice+1) << ":\n"
                     << "==========================" << endl
                     << ansA << " of " << students << " ("
                     << setprecision(2) << fixed << fAnsA << "%) selected response A.\n"
                     << ansB << " of " << students << " ("
                     << setprecision(2) << fixed << fAnsB << "%) selected response B.\n"
                     << ansC << " of " << students << " ("
                     << setprecision(2) << fixed << fAnsC << "%) selected response C.\n"
                     << ansD << " of " << students << " ("
                     << setprecision(2) << fixed << fAnsD << "%) selected response D.\n"
                     << ansS << " of " << students << " ("
                     << setprecision(2) << fAnsS << "%) selected no response.\n\n"
                     << ansCorrect << " of " << students << " ("
                     << setprecision(2) << fixed << fAnsCorrect
                     << "%) selected the correct response.\n\n"
                     << "Would you like to look up another question (Y/N):";

                cin.clear(); //clears input
                cin.ignore(100, '\n');

                //asks if user wants to view another question
                while(!(badInput))
                {
                    if(!(cin >> charChoice))
                    {
                        cin.clear();
                        cin.ignore(100, '\n');
                        cout << "Bad input, try again:";
                    }
                    else //if input type accepted
                    {
                        if(toupper(charChoice) == 'Y')
                            stats(students, grades, answers, questions);
                        else if(toupper(charChoice) == 'N')
                            badInput = true;//set to exit new while
                        else //if bad char
                        {
                            cin.clear();
                            cin.ignore(100, '\n');
                            cout << "Incorrect input, try again:";
                        }
                    }
                }//end while
                badInput = false;//resets input to exit original while
            }//end else
        }//end else
    }while(badInput);
}

/* writeGrades Method
Writes grades to an output file "testResults.txt"

Arguments: int sAmt, string **grades, string answers
Returns  : void
*/
void writeGrades(int students, string **grades, string answers)
{
    ofstream oFile;
    oFile.open("testResults.txt");
    int gradeWidth; //variable for handling perfect scores
    string borders; //string for variables

    borders += "| ";
    for(int i = 0; i < 69; i++)
        borders += "-";
    borders += "|\n";

    oFile << "INDIVIDUAL TEST RESULTS\n"
          << "=========================\n\n" << borders
          << "| " << setw(9) << left << "NAME" << "|"
          << setw(59) << " ANSWERS" << "| GRADE |"
          << endl << borders;

    for(int j = 0; j < students; j++)
    {
        oFile << "| "; //for borders

        for(int k = 0; k < 4; k++)
        {
            if(k == 0)
                oFile << grades[j][k] << "\t|";
            else //if not student names row
            {
                oFile << right;

                if(k == 2) //number grades row
                {
                    gradeWidth = 5;

                    if(grades[j][k] == "100")
                        gradeWidth--; //handles perfect score

                    oFile << "\t| " << setw(gradeWidth)
                          << left << grades[j][k] << "| ";
                }
                else if(k == 3) //letter grade row
                {
                    oFile << setw(3) << left << grades[j][k] << "|";
                }
                else
                    oFile << grades[j][k];
            }
        }
        oFile << endl;
    }
    oFile << borders << endl;

    //mini stats
    oFile << "\t# of students: " << students << endl
          << "\tAnswer Key: \'" << answers << "\'" << endl;

    oFile.close();

    cout << ".\n.\n.\nFile Output!\n\n";
}

/* readFile Method
Reads the file given for information and puts
into array.

Arguments: ifstream& iFile, string **grades, int students
Returns  : string **grades
*/
string **readFile(ifstream& iFile, string **grades, int students)
{
    //array init.
    grades = new string*[students]();
    for(int i = 0; i < students; i++)
        grades[i] = new string[4]();

    //fills array
    for(int i = 0; i < students; i++)
    {
        for(int j = 0; j < 4; j++)
        {
            if(j == 0)
                iFile >> grades[i][j];
            else if(j == 1)
            {
                iFile.get(); // skips a space
                getline(iFile, grades[i][j]);
            }
        }
    }

    return grades;
}

/* gradeTests Method
Takes grades array and grades both letter and number grades.
Puts grades into array to be used for file writing output.

Arguments: string **grades, string answers, int students, int questions
Returns  : float studentGrades (array)
*/
float *gradeTests(string **grades, string answers, int students, int questions)
{
    float studentNumGrade;
    int correctCount, incorrectCount;

    //array for student number grades
    float *studentGrades;
    studentGrades = new float[students];

    for(int i = 0; i < students; i++)
    {
        studentNumGrade = 0;  //
        correctCount    = 0; //reset for new students
        incorrectCount  = 0;//

        for(int j = 0; j < questions; j++)
        {
            if(answers[j] == grades[i][1][j])
                correctCount++;
            else if(grades[i][1][j] != ' ')
                incorrectCount++;
        }
        studentNumGrade = ((correctCount * 4) - (incorrectCount * 2));
        studentNumGrade = (float(studentNumGrade) / (questions * 4) * 100);

        //assign letter grades
        if(studentNumGrade < 60)
            grades[i][3] = "F";
        else if(studentNumGrade < 70)
            grades[i][3] = "D";
        else if(studentNumGrade < 80)
            grades[i][3] = "C";
        else if(studentNumGrade < 90)
            grades[i][3] = "B";
        else //if student gets =< 90
            grades[i][3] = "A";

        //assign number grade into fstream
        stringstream ss; //declares variable to convert float to string
        ss << studentNumGrade; //puts float studentNumGrade into stringstream
        grades[i][2] = ss.str(); //assignment

        //assign number grade to float array for class avg use
        studentGrades[i] = studentNumGrade;
    }

    return studentGrades;
}

/* classAverages Method
Output the class average in both number and letter format

Arguments: int questions, int students, string answers, float *studentGrades
Returns  : void
*/
void classAverages(string answers, int students, int question, float *studentGrades)
{
    float totalNum = 0;
    float avgNum = 0;
    char  avgL;

    for(int i = 0; i < students; i++)
    {
        totalNum += studentGrades[i];
    }

    avgNum = (totalNum / students); //calculate

    //assign letter grades
    if(avgNum < 60)
        avgL = 'F';
    else if(avgNum < 70)
        avgL = 'D';
    else if(avgNum < 80)
        avgL = 'C';
    else if(avgNum < 90)
        avgL = 'B';
    else //if student gets =< 90
        avgL = 'A';

    //class avg output
    cout << endl << "The class average was a " << avgL << " with a "
         << setprecision(2) << fixed << avgNum << "%.";

    if(avgL == 'F' || avgL == 'D')
        cout << endl << "Uh oh...\n\n";
    else if(avgL == 'A')
        cout << endl << "Woohoo!\n\n";
    else
        cout << endl << endl;

    //delete float array
    delete []studentGrades;
}

int main()
{
    ifstream iFile("testScores.txt");
    string answers;
    int students, questions;
    int input = 0;
    bool quit = false;

    cout << "Welcome!\n";

    if(iFile.is_open())
    {
        //read in # of questions and students, and answers line
        iFile >> questions >> students >> answers;
        //read in student information
        string **grades = readFile(iFile, grades, students);
        iFile.close(); //closes file reading stream
        //returns a set of student number grades for output of class averages
        float *sGrades = gradeTests(grades, answers, students, questions);
        classAverages(answers, students, questions, sGrades);

        do
        {
            input = prompt();

            switch(input)
            {
            case 1:
                writeGrades(students, grades, answers);
                break;
            case 2:
                stats(students, grades, answers, questions);
                break;
            case 3:
                //erasing pointers
                for(int i = 0; i < students; i++)
                    delete []grades[i];
                delete []grades;

                quit = true;
                break;
            }
        }while(!(quit));
    }
    else //if file fails to open
    {
        cout << "ERROR: File Not Found!\n";
    }

    return 0;
}//end main
