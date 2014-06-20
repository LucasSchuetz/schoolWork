##
#	CS 161 Program Two: For Loop Control
#
#	Reporting Average Snowfall in Inches 
#
#	Author: Lucas Schuetz
#	October 20th 2013
##

def main():
	#Declaring Variables
	snowfallStr = 0
	snowfall = 0
	avgSnowfall = 0
	totalSnowfall = 0
	#Intro
	print("I would like to calculate the average snowfall in your area for a week.")
	area = input("Where do you live? ")
	print("")
	print(area + "'s Snowfall")
	print("=====================")
	#Taking Inputs
	for day in range(7):
		day = day + 1
		snowfall = float(input("Please enter the snowfall for day " + str(day) + " in inches: "))
		print("Day " + str(day) + ": " + str(snowfall))
		totalSnowfall = snowfall + totalSnowfall
	avgSnowfall = round(totalSnowfall / 7, 2)
	totalSnowfall = round(totalSnowfall, 2)
	print("")
	print("The total snowfall for the week was " + str(totalSnowfall) + "in.")
	print("The average snowfall for the week was " + str(avgSnowfall) + "in. ")
main()