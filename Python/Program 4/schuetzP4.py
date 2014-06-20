##########################################################################
#	Program 4: Reporting a Class Grade                                   #
#	                                                                     #
#	About: Here we will be taking inputs for assignments from students,  #
#			calculating their overall grades, outputting each grade,     #
#			 and outputting the class average and total. Enjoy!          #
#	                                                                     #
#	Author: Lucas Schuetz                                                #
#	Date: November 21, 2013                                              #
##########################################################################

##########################################################################
#	Function computeScore                                                #
#  This function asks for inputs for all grades for each student and     #
#  return students finalScore in the class                               #
##########################################################################
def computeScore():
	scores     = [0]*12
	exams      = 0.0
	homework   = 0.0
	projects   = 0.0
	scoreCount = 0
	finalScore = 0.0
	
	# Takes student score inputs, stores each in an array
	while scoreCount < 12:
		if scoreCount <= 3:
			scores[scoreCount] = int(input("Enter an exam score: "))
			exams = exams + scores[scoreCount]
		elif scoreCount > 3 and scoreCount <= 8:
			scores[scoreCount] = int(input("Enter a homework score: "))
			homework = homework + scores[scoreCount]
		else:
			scores[scoreCount] = int(input("Enter a project score: "))
			projects = projects + scores[scoreCount]	
		scoreCount += 1
		# End while
		
	# Computes each sections grades and compiles all for final score
	exams    = exams * 40 / 400
	homework = homework * 10 / 50
	projects = projects * 50 / 450
	finalScore = round(exams + homework + projects, 2)
	
	return finalScore

##########################################################################
#	Function computeLetterGrade                                          #
#  This function is given scores (both individual and class) and         #
#  computes and returns the letter grade for each to be output           #
#  in a final print                                                      #
##########################################################################
def computeLetterGrade(score):
	if score < 60:
		grade = "F"
	elif score < 70:
		grade = "D"
	elif score < 80:
		grade = "C"
	elif score < 90:
		grade = "B"
	else:
		grade = "A"
	return grade

##########################################################################
#	Main Function                                                        #
##########################################################################
def main():
	# Initial Variable Declarations
	classAverage  = 0.0
	classAvgGrade = "A"
	studentScore  = 0.0
	classTotal    = 0.0
	studentCount  = 0
	gradeReport   = "\nStudent\tScore\tGrade\n============================\n"
	
	# First student name input
	studentName = input("Enter the first student's name ('quit' when done): ")
	
	# While loop, repeats until class grades are finished being input
	# Enter 'quit' to finish
	while studentName != "quit":
		
		# Calls functions to compute scores and letter grades
		# See above for more detailed description
		studentScore = computeScore()
		studentGrade = computeLetterGrade(studentScore)
	
		# Compiles each students score and grade into one output
		gradeReport = gradeReport + "\n" + studentName + ":\t%6.1f" % studentScore + "\t" + studentGrade
	
		classAverage = classAverage + studentScore
		studentCount += 1
			
		studentName = input("Enter the next student's name ('quit' when done): ")
		# End while
		
	# Computes class total average score and then
	# Calls function to compute the class letter grade
	classAverage = round(classAverage / studentCount, 2)
	classAvgGrade = computeLetterGrade(classAverage)
	
	print(gradeReport)
	print("Average class Score: ", classAverage, "\nAverage class grade: ", classAvgGrade)
	
	test = input("working")	
main()