  /*
  Lucas Schuetz
  CS221 Spring 2014
  Assignment # 2
  Description: This program allows the user to either battle themselves, a friend,
  or a supplied AI. User may choose a character and whether to battle the AI or not.
  Battle commences, winner is declared at the end.
  */

  #include <iostream>
  #include <cstdlib>
  #include <fstream>
  #include <ctime>
  #include <string>
  #include <cctype>
  using namespace std;

  int main()
  {
  int hp, totalHp, mHp, totalMHp; //Health pools
  int minDmg, maxDmg, minSDmg, maxSDmg, minPot, maxPot; //Damage and Potion values
  int dmg, intInput; //Potion, damage, and intInput values
  int turn = 0; //Tracks turns
  char charInput; //Input for battle
  string name, mName, spellName, mSpellName; //character, monster, and spell names
  bool flag, defend, mDefend = false; //Catch for bad inputs and fight phase
  srand(time(NULL));
  ifstream iFile;
  ofstream oFile;

  //Class Splash Logos
  string wSplash = "You have chosen Wilbur the...\n\n";
  wSplash += "  /\\   W  ______\n  ||   A | *--* |\n";
  wSplash += "  ||   R | |<>| |\n  ||   R | |--| |\n";
  wSplash += "  ||   I | |<>| |\n<****> O  \\____/\n";
  wSplash += "  ||   R\n  \\/ \n\n";
  string sSplash = "You have chosen Sarah the...\n\n";
  sSplash += "  Sorcerer   /\\\n";
  sSplash += " _____      / /\n|  |  |    / /\n";
  sSplash += "| -|- |   / /\n|  |  |  / /\n";
  sSplash += "|_____| / /\n        \\/\n\n";
  string aSplash = "You have chosen Andy the...\n\n";
  aSplash += "    |\\    /|\n     \\\\  //\n";
  aSplash += "      \\\\//\n      //\\\\\n";
  aSplash += "   |\\//  \\\\/|\n   |__\\  /__|\n";
  aSplash += "  //         \\\\\n *  ASSASSIN   *\n\n";

  while(!flag) //Loops until valid character is chosen
  {
      cout << "Welcome to the Battle Arena!\n\n"
           << "What class would you like to play as?\n"
           << "1. Warrior\n2. Sorcerer\n3. Assassin\n"
           << "4. Your own Character\n";
      if(!(cin >> intInput))
      {
          cin.clear();
          cin.ignore(1000, '\n');
          cout << "\n\nBad Input! Try again please!\n\n";
      }
      else if(intInput < 1 || intInput > 5)
      {
          cin.clear();
          cin.ignore(1000, '\n');
          cout << "\n\nBad Input! Try again please!\n\n";
      }
      else
      {
          switch(intInput)
          {
          case 1:
              iFile.open("warriorCharInfo.txt");
              cout << wSplash;
              break;
          case 2:
              iFile.open("sorcererCharInfo.txt");
              cout << sSplash;
              break;
          case 3:
              iFile.open("assassinCharInfo.txt");
              cout << aSplash;
              break;
          case 4:
              iFile.open("characterInfo.txt");
              cout << "You have chosen your own class!\n\n";
              break;
          }
          //Validate user choice
          cout << "Is this correct? (Y/N)\n";
          if(!(cin >> charInput))
          {
              cin.clear();
              cin.ignore(1000, '\n');
              cout << "\n\nBad Input! Starting over...\n\n";
          }
          else
          {
              if(toupper(charInput) == 'N')
              {
                  cout << "\nTry and choose more wisely this time.\n\n";
                  iFile.close();
              }
              else if(toupper(charInput) == 'Y')
              {
                  flag = true;
              }
              else
              {
                  cin.clear();
                  cin.ignore(1000, '\n');
                  cout << "\n\nBad Input! Starting Over...\n\n";
                  iFile.close();
              }
          }
      }
  }
  flag = false; //Resets flag for future use

  if(iFile.is_open())
  {
      while(iFile >> name >> hp >> spellName >> mName >> mHp >> mSpellName
                  >> minDmg >> maxDmg >> minSDmg >> maxSDmg >> minPot >> maxPot)
      {
          totalHp  = hp;   //Sets total health pools for
          totalMHp = mHp; //  display during fight phase

          while(!flag) //Loops while input is incorrect
          {
              cout << "Would you like to fight the AI?(Y/N)\n";
              if(!(cin >> charInput))
              {
                  cin.clear();
                  cin.ignore(1000, '\n');
                  cout << "\n\nBad Input! Try again please!\n\n";
              }
              else
              {
                  flag = true; //Set true to exit while
                  cout << "Today we have " << name
                       << " fighting " << mName << "!\n\n";

                  if(toupper(charInput) == 'Y') //Battling AI
                  {
                      do
                      {
                          if(turn % 2 == 0) //Player turn
                          {
                              cin.ignore(1000, '\n');
                              cin.clear();
                              cout << "It's " << name << "\'s turn! ("
                                   << hp << "/" << totalHp << "HP)\n"
                                   << "What will they choose to do?\n\n"
                                   << "(A)ttack\n(S)pell Ability\n"
                                   << "(U)se Potion\n(D)efend\n\n";
                              turn++;

                              if(!(cin >> charInput))
                              {
                                  cin.clear();
                                  cin.ignore(1000, '\n');
                                  cout << "\nBad Move! Try again please!\n\n";
                              }
                              else
                              {
                                  if(toupper(charInput) == 'A')//attack
                                  {
                                      dmg = (rand() % ((maxDmg - minDmg) + 1) + minDmg);

                                      if(mDefend)
                                      {
                                          dmg /= 2;
                                          mDefend = false;
                                      }

                                      mHp -= dmg;
                                      cout << name << " hits " << mName
                                           << " for " << dmg << "!\n\n";
                                  }
                                  else if(toupper(charInput) == 'S')//spell
                                  {
                                      dmg = (rand() % ((maxSDmg - minSDmg) + 1) + minSDmg);
                                      mHp -= dmg;
                                      cout << name << " uses " << spellName
                                           << " and hits for " << dmg << "!\n\n";
                                  }
                                  else if(toupper(charInput) == 'U')//potion
                                  {
                                      //Potion, int dmg used for potion
                                      dmg = (rand() % ((maxPot - minPot) + 1) + minPot);
                                      hp += dmg;

                                      if(hp > totalHp)
                                      {
                                          dmg = hp - totalHp;
                                          hp = totalHp;

                                          cout << name << " uses a potion and heals "
                                               << "to full health! ("
                                               << dmg << " overheal)\n\n";
                                      }
                                      else
                                      {
                                          cout << name << " uses a potion and heals "
                                               << " for " << dmg << "!\n\n";
                                      }
                                  }
                                  else if(toupper(charInput) == 'D')//defend
                                  {
                                      cout << name << " cowers in fear!\n\n";
                                      defend = true;
                                  }
                                  else
                                  {
                                      cin.clear();
                                      cin.ignore(1000, '\n');
                                      cout << "\n\nBad Move! Try again please!\n\n";
                                  }
                              }
                          }
                          else //AI Move
                          {
                              int turnAI = (rand() % 10);

                              switch(turnAI)
                              {
                              case 0:    //
                              case 1:   //
                              case 2:  // attack
                              case 3: //
                              case 4://
                                  {
                                      dmg = (rand() % ((maxDmg - minDmg) + 1) + minDmg);

                                      if(defend)
                                      {
                                          dmg /= 2;
                                          defend = false;
                                      }

                                      hp -= dmg;
                                      cout << mName << " hits " << name
                                           << " for " << dmg << "!\n";
                                      break;
                                  }
                              case 5:  //defend
                              case 6: //
                                  {
                                      cout << mName << " cowers in fear!\n";
                                      mDefend = true;
                                      break;
                                  }
                              case 7:  //spell
                              case 8: //
                                  {
                                      dmg = (rand() % ((maxSDmg - minSDmg) + 1) + minSDmg);
                                      hp -= dmg;
                                      cout << mName << " uses " << mSpellName
                                           << " and hits for " << dmg << "!\n";
                                      break;
                                  }
                              case 9: //potion
                                  {
                                      //Potion, int dmg used for potion
                                      dmg = (rand() % ((maxPot - minPot) + 1) + minPot);
                                      hp += dmg;

                                      if(hp > totalHp)
                                      {
                                          dmg = hp - totalHp;
                                          hp = totalHp;

                                          cout << name << " uses a potion and heals "
                                               << "to full health! ("
                                               << dmg << " overheal)\n\n";
                                      }
                                      else
                                      {
                                          cout << name << " uses a potion and heals "
                                               << " for " << dmg << "!\n";
                                      }
                                      break;
                                  }
                              }
                              cout << mName << " has " << mHp << " health left! "
                                   << "(" << mHp << "/" << totalMHp << ")\n\n";
                              turn++;
                          }
                      }while(hp > 0 && mHp > 0);
                  }
                  else if(toupper(charInput) == 'N') //Not battleing AI
                  {
                      do
                      {
                          if((turn % 2) == 1)
                          {
                              cin.ignore(1000, '\n');
                              cin.clear();
                              cout << "It's " << mName << "\'s turn! ("
                                   << mHp << "/" << totalMHp << "HP)\n"
                                   << "What will they choose to do?\n\n"
                                   << "(A)ttack\n(S)pell Ability\n"
                                   << "(U)se Potion\n(D)efend\n";
                              turn++;
                          }
                          else
                          {
                              cin.ignore(1000, '\n');
                              cin.clear();
                              cout << "It's " << name << "\'s turn! ("
                                   << hp << "/" << totalHp << "HP)\n"
                                   << "What will they choose to do?\n\n"
                                   << "(A)ttack\n(S)pell Ability\n"
                                   << "(U)se Potion\n(D)efend\n";
                              turn++;
                          }
                          if(!(cin >> charInput))
                          {
                              cin.clear();
                              cin.ignore(1000, '\n');
                              cout << "\n\nBad Move! Try again please!\n\n";
                          }
                          else
                          {
                              cout << endl; //Spacing
                              if(toupper(charInput) == 'A')//attack
                              {
                                  if((turn % 2) == 0)
                                  {
                                      dmg = (rand() % ((maxDmg - minDmg) + 1) + minDmg);

                                      if(defend)
                                      {
                                          dmg /= 2;
                                          defend = false;
                                      }

                                      hp -= dmg;
                                      cout << mName << " hits " << name
                                          << " for " << dmg << "!\n\n";
                                  }
                                  else
                                  {
                                      dmg = (rand() % ((maxDmg - minDmg) + 1) + minDmg);

                                      if(mDefend)
                                      {
                                          dmg /= 2;
                                          mDefend = false;
                                      }

                                      mHp -= dmg;
                                      cout << name << " hits " << mName
                                           << " for " << dmg << "!\n\n";
                                  }
                              }
                              else if(toupper(charInput) == 'S')//spell
                              {
                                  if((turn % 2) == 0)
                                  {
                                      dmg = (rand() % ((maxSDmg - minSDmg) + 1) + minSDmg);
                                      hp -= dmg;
                                      cout << mName << " uses " << mSpellName
                                           << " and hits for " << dmg << "!\n\n";
                                  }
                                  else
                                  {
                                      dmg = (rand() % ((maxSDmg - minSDmg) + 1) + minSDmg);
                                      mHp -= dmg;
                                      cout << name << " uses " << spellName
                                           << " and hits for " << dmg << "!\n\n";
                                  }
                              }
                              else if(toupper(charInput) == 'U')//potion
                              {
                                  //Potion, int dmg used for potion
                                  dmg = (rand() % ((maxPot - minPot) + 1) + minPot);

                                  if((turn % 2) == 0)
                                  {
                                      mHp += dmg;

                                      if(mHp > totalMHp)
                                      {
                                          dmg = mHp - totalMHp;
                                          mHp = totalMHp;

                                          cout << mName << " uses a potion and heals "
                                               << "to full health! ("
                                               << dmg << " overheal)\n\n";
                                      }
                                      else
                                      {
                                          cout << mName << " uses a potion and heals "
                                               << " for " << dmg << "!\n\n";
                                      }
                                  }
                                  else
                                  {
                                      hp += dmg;

                                      if(hp > totalHp)
                                      {
                                          dmg = hp - totalHp;
                                          hp = totalHp;

                                          cout << name << " uses a potion and heals "
                                               << "to full health! ("
                                               << dmg << " overheal)\n\n";
                                      }
                                      else
                                      {
                                          cout << name << " uses a potion and heals "
                                               << " for " << dmg << "!\n\n";
                                      }
                                  }
                              }
                              else if(toupper(charInput) == 'D')//defend
                              {
                                  if((turn % 2) == 0)
                                  {
                                      cout << mName << " cowers in fear!\n\n";
                                      mDefend = true;
                                  }
                                  else
                                  {
                                      cout << name << " cowers in fear!\n\n";
                                      defend = true;
                                  }
                              }
                              else
                              {
                                  cin.clear();
                                  cin.ignore(1000, '\n');
                                  cout << "\n\nBad Move! Try again please!\n\n";
                              }
                          }
                      }while(hp > 0 && mHp > 0);
                      //Checks for winner and outputs
                      if(mHp < 0)
                      {
                          cout << "Today's winner is " << name << "!\n\n";
                      }
                      else
                      {
                          cout << "Todays' winner is " << mName << "!\n\n";
                      }
                  }
                  else
                  {
                      cin.clear();
                      cin.ignore(1000, '\n');
                      cout << "\n\nDoes not compute! Try again\n\n";
                  }
              }//end else fight phase
          }//end while flag
      }//end while iFile inputs
  }//end if file is open
  else
  {
      cout << "Error: File Not Found!\nFarewell!\n\n";
  }

  return 0;
  }//end main
