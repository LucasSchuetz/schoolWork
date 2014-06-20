##
#	Program 3: Sentinel-Controlled Iteration
#
#	This program will produce a report of money earning by "The Family"
#
#	Author: Lucas Schuetz
#	Date Written: 10/30/13
##

def main():
	#Variable Declaration
	associateName      = ""
	bestAssociate      = ""
	worstAssociate     = ""
	totalOutput        = ""
	amtAvg             = 0
	numAssociates      = 0
	amtCollected       = 0.0
	prevAmt            = 0.0
	amtTotal           = 0.0

	#Intro
	print("======================================================================")
	print(" This will collect all the associate incomes and calculate the winner")
	print("======================================================================\n")

	#First associates name
	associateName = input("Enter the name of the first associate: ")

	#Control Loop -- Grabs names and amount from associates until "DOA" is entered.
	while associateName != "DOA":
		totalOutput = (totalOutput + associateName + ": %15s" % "$")

		amtCollected = float(input("Enter the amount collected from " + associateName + ": $"))
		totalOutput = (totalOutput + "%1.2f" % amtCollected + "\n")

		#Allows following statement to execute correctly is first amtCollected is smallest amount
		if prevAmt == 0:
			prevAmt = amtCollected
		else:
			prevAmt = prevAmt

		print(amtCollected)

		#Calculating Largest/Smallest Collections
		if amtCollected > prevAmt:
			bestAssociate = associateName + " with $%0.2f" % amtCollected

		elif amtCollected < prevAmt:
			worstAssociate = associateName + " with $%0.2f" % amtCollected

		else:
			bestAssociate = associateName
			worstAssociate = associateName

		#Sets amount collected to new variable for use in if structure
		prevAmt = amtCollected
		associateName = input("Enter the name of the next associate: ")

		amtTotal = (amtTotal + amtCollected)
		numAssociates += 1

	#Average's
	amtAvg = round(amtTotal / numAssociates, 2)

	#Output
	print("\n-=- The Family Insurance Report -=-\n")
	print(totalOutput)

	print("\nTotal Collections are $" + str(amtTotal))
	print("\nAverage Collection is $" + str(amtAvg))

	print("\nMost Collected From: " + bestAssociate)
	print("\nLeast Collected From: " + worstAssociate)
main()