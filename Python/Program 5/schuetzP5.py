#####################################################
#                                                   #
#   Program 5: Dining Hall - This program will be   #
#	asking the manager for a list of entrees        #
#	that will then be voted upon by the students    #
#	which then will be tallied up and output        #
#	in both a bar chart and totals+percentages.     #
#                                                   #
#	Author: Lucas Schuetz                           #
#	Date  : November 28th 2013                      #
#                                                   #
#####################################################

#####################################################
# --directionOutput-- The initial prompt given to   #
# manager, more of a hello/good morning.            #
#####################################################
def directionsOutput():
	# Variable Declarations
	headerFooter = "="*50 + "\n"

	# Initial prompt for manager
	print(headerFooter + "Welcome to the University Dining Hall " + \
	      "Survey Program\n" + "-"*50 +  "\n" + \
		  "Please enter the number of dishes" + \
	      " you would like to include, followed by their names. " + \
	      "The program will then ask diners what their favorite was." + \
	      " When mealtime is over, any negative number will exit " + \
	      "and print out total tallies for each meal, percentages, " + \
	      "and a graph showing the diner's preferences.\n" + headerFooter)
	
	# Ends directionsOutput -- Return nothing.

#####################################################
# --managerSetup-- Asks the manager for input       #
# of how many choices, what choices the are, stores #
# the choices in a list, adds "None of the Above"   #
# and returns the list.                             #
#####################################################

def managerSetup():
	# Variable Declarations
	foodChoiceNum = 0
	foodList      = [""]
	
	# Manager Input
	foodChoiceNum = int(input("How many entrees do we have today? "))
	foodList = [""]*(foodChoiceNum + 1)
	
	# Loop for choices of food
	for i in range(len(foodList)-1):
		foodList[i] = input("Please enter the name of dish " + \
							str(i+1) + ": ")
	# End Loop
	
	# Adding "None of the Above" option
	foodList[foodChoiceNum] = "None of the Above"
	
	return foodList
	# End managerSetup -- Returns foodList array

#####################################################
# --studentSurvey-- Takes managers foodList inputs, #
# outputs the list and asks each student which      #
# choice they liked most, if any, and saves choice  #
# for later output. Returns a tally of choice totals#
#####################################################

def studentSurvey(foodList):
	# Variable Declaration
	foodPrompt    = "|" + "="*38 +"|\n"
	
	# Creating survey prompt for students
	foodPrompt += "| Please choose your favorite entree:" + " "*2 + "|\n" \
	"|" + "-"*38 +"|\n"
	for i in range(len(foodList)):
		foodPrompt += "|%21s" % foodList[i] + "|" + " "*10 + str(i+1) + \
					  "%7s" % " |\n"
	foodPrompt += "|" + "="*38 +"|\n"
	
	return foodPrompt # End studentSurvey -- Returns foodPrompt
	
#####################################################
# --surveyTally-- Takes prompt and list of foods    #
# and takes a survey of favorite choices that are   #
# put into a final tallied output for manager       #
#####################################################

def surveyTally(prompt, foodList):
	#Variable declarations
	studentVote     = 0
	totalVotes      = 0
	managerOverride = False
	tally           = [0]*(len(foodList))
	tallyPrcnt      = [0.0]*(len(foodList))
	finalOutput     = ""
	
	# Output for tally total and %'s
	tallyOutput = "|" + "="*46 + "|\n" + \
				  "|" + " "*17 + "--Results--" + " "*18 + "|\n" + \
				  "|" + "="*46 + "|\n" + \
				  "|" + " "*14 + "-Meal-     -Votes-" + \
				  " "*4 + "-Percent- " + "|\n" + \
				  "|" + "-"*46 + "|\n"
				  
	# Formatting for histogram
	histogramOutput = "|" + "="*60 + "|\n" + \
					  "|" + " "*22 + "--Histogram--" + " "*25 + "|\n" + \
					  "|" + "-"*60 + "|\n"
	
	# Beginning of Survey
	# Loops for student input -- Negative number to exit
	while managerOverride == False:
	
		studentVote = int(input(prompt + "%10s" % ">> "))
		
		# Error catch for invalid number choice
		# Sends back to beginning if first choice is negative
		if studentVote > (len(foodList)) or studentVote == 0:
			print("\n\n--ERROR -- Please input valid number!\n")		
		elif studentVote > 0:
			tally[(studentVote - 1)] += 1
			totalVotes += 1
		elif studentVote < 0 and totalVotes == 0: # Catch for a negative on first entry!
			print("\n"*15)                        # Clears screen and returns
			main()                                # to beginning of program.
		else:
			managerOverride = True

	# Calculating percentages from tallies
	for i in range(len(foodList)):
		tallyPrcnt[i] = (tally[i] / totalVotes) * 100
	
	# Formatting and Concocting final output
	for i in range(len(foodList)):
		tallyOutput += "|%20s" % foodList[i] + " >>%6d" % tally[i] + \
					   "   >>  %6s" % str(round(tallyPrcnt[i], 2)) + \
					   "%" + " "*3 + "|\n"
	tallyOutput += "|" + "="*46 +"|\n\n"
	
	# Formatting histogram
	for i in range(len(foodList)):
		histogramOutput += "|%20s" % foodList[i] + "|" + \
						   "*"*int(tallyPrcnt[i]*2) + "\n"
	histogramOutput += "|" + "="*60 + "|\n"
		
	finalOutput = tallyOutput + histogramOutput
	
	return finalOutput # End surveyTally -- Returns finalOutput
	
#####################################################
#  --Main Function--                                #
#####################################################

def main():
	# Initial set-up
	directionsOutput()

	# Manager Function
	foodList = managerSetup()
	
	# Student Input Function
	prompt = studentSurvey(foodList)

	# Survey function
	finalOutput = surveyTally(prompt, foodList)
	
	# The final output for manager
	print(finalOutput)
	
main() # End Program