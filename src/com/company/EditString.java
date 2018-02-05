package com.company;
import java.util.Scanner;

public class EditString extends ArrayBoundedStack {
    public static void main(String[] args) { //driver method
        System.out.println("Enter a string that may be edited based on your next inputs. This string may be anything."); //prompting user to enter any String
        Scanner input = new Scanner(System.in); //creating Scanner to read this message
        String userMessage = input.nextLine(); //creating String variable that will hold the message the user inputs
        System.out.println("\nFinal Output: " + EditString.manipulateMessage(userMessage, EditString.obtainUserInstruction())); //sending the user's message to two other methods in order to determine how it should be changed, then printing the final result out
        }

        private static String obtainUserInstruction() { //method that will obtain the user's instructions
            String userInstruction = ""; //String that will hold the entire instructions of a user
            String letterOfUserInstruction = ""; //String that will hold one letter from the instructions
            System.out.println("\nEnter the following based on what you want to do:\n\"U\" to make all letters uppercase\n\"L\" to make all letters lowercase\n\"R\" to reverse the string\n\"C\" to change a certain character in the string to a new character (for example, if you do \"C A T\", this changes all instances of \"A\" to \"T\")\n\"Z\" to undo the most recent change\n\"X\" to stop making changes, however, you can still undo the most recent change after entering \"X\"."); //prompts user to enter instructions
            Scanner input = new Scanner(System.in); //creating another Scanner to read the user instructions

            while(!letterOfUserInstruction.equals("X")) { //while the user wants to continue making changes to the message...
                letterOfUserInstruction = input.next(); //get the next letter the user inputs
                if (letterOfUserInstruction.equals("Z")) //if that letter is Z...
                    if (userInstruction.contains("C") && userInstruction.substring(userInstruction.length() - 3, userInstruction.length() - 2).equals("C")) //if the message also contains a C and there is a C command right before the Z command...
                        userInstruction = userInstruction.substring(0, userInstruction.length() - 3); //then remove the C and Z command (Z is not needed when manipulating string)
                    else
                        userInstruction = userInstruction.substring(0, userInstruction.length() - 1); //if there is not a C or a C is not right before the Z, then just remove the Z command (Z is not needed when manipulating string)
                else
                    userInstruction += letterOfUserInstruction; //if that letter is not Z then simply add it to the String of instructions
            }
            if(input.next().equals("Z")) { //if there is a Z right after an X
                if(userInstruction.contains("C") && userInstruction.substring(userInstruction.length() - 4, userInstruction.length() - 3).equals("C")) //if the message also contains a C and there is a C command right before the X command...
                    userInstruction = userInstruction.substring(0, userInstruction.length() - 4); //then remove the C, X, and Z commands
                else
                    userInstruction = userInstruction.substring(0, userInstruction.length() - 2); //then remove the X and Z command (neither are needed when manipulating the message)
            }
            input.close(); //close the Scanner after obtaining the user instructions in order to minimize resource leak
            return userInstruction; //return the final String with the instructions
        }

        private static String manipulateMessage(String mes, String ins) { //method that will change the message based on the user's instructions
            String userMessage = mes; //String that holds original message
            String userInstruction = ins; //String that holds instructions
            String letterOfUserInstruction = ""; //String that holds single letter of the instructions
            int maxIndex = userMessage.length(); //int that holds the total length of the message
            StackInterface<String> lettersArray = new ArrayBoundedStack<>(maxIndex); //Stack that will be used to hold the individual letters of a message, max size is based on the number of letters in the message
            String manipulatedMessage = userMessage; //At first, the manipulated message is equivalent to the original message because the user instructions have not been applied to it yet, this is declared in case no user instruction's were given

            for(int i = maxIndex; i > 0; i--) { //for every letter in the message...
                lettersArray.push(userMessage.substring(i - 1, i)); //add every individual letter in the message to an element in the stack, the first letter of the message is at the top
            }

            for(int j = 0; j < userInstruction.length(); j++) { //for each individual instruction given to the user...
                letterOfUserInstruction = userInstruction.substring(j, j + 1); //determine letter we are looking at and storing it in a String
                if (letterOfUserInstruction.equals("U")) { //if the letter is U...
                    manipulatedMessage = ""; //clear manipulated message because it will now change based on the new instruction given by the letter
                    while (!lettersArray.isEmpty()) { //while the Stack is not empty...
                        manipulatedMessage += lettersArray.top().toUpperCase(); //the manipulated message is equivalent to the previous but in all uppercase
                        lettersArray.pop(); //remove the top element from the Stack so we can get the next letter in the message
                    }
                    for (int i = maxIndex - 1; i >= 0; i--) //for each letter in the message...
                        lettersArray.push(manipulatedMessage.substring(i, i + 1)); //add the letter back into the Stack
                }
                if (letterOfUserInstruction.equals("L")) { //if the letter is U...
                    manipulatedMessage = ""; //clear manipulated message because it will now change based on the new instruction given by the letter
                    while (!lettersArray.isEmpty()) { //while the Stack is not empty...
                        manipulatedMessage += lettersArray.top().toLowerCase(); //the manipulated message is equivalent to the previous but in all lowercase
                        lettersArray.pop(); //remove the top element from the Stack so we can get the next letter in the message
                    }
                    for (int i = maxIndex - 1; i >= 0; i--) //for each letter in the message...
                        lettersArray.push(manipulatedMessage.substring(i, i + 1)); //add the letter back into the Stack
                }
                if (letterOfUserInstruction.equals("R")) { //if the letter is U...
                    manipulatedMessage = ""; //clear manipulated message because it will now change based on the new instruction given by the letter
                    while (!lettersArray.isEmpty()) { //while the Stack is not empty...
                        manipulatedMessage += lettersArray.top(); //the manipulated message is equivalent to the previous
                        lettersArray.pop(); //remove the top element from the Stack so we can get the next letter in the message
                    }
                    for (int i = 0; i < maxIndex; i++) //for each letter in the message
                        lettersArray.push(manipulatedMessage.substring(i, i + 1)); //add the letter back into the Stack, but with the first letter at the very bottom
                    for (int i = 0; i < maxIndex; i++) { //for each letter in the message
                        manipulatedMessage += lettersArray.top(); //the first half of the manipulated message now contains the previous message, while the last half contains the previous in reverse
                        lettersArray.pop(); //remove the top element from the Stack so we can get the next letter in the message, but backwards
                    }
                    manipulatedMessage = manipulatedMessage.substring(maxIndex, manipulatedMessage.length()); //making the manipulated message only equal to the previous message but in reverse
                }
                if (letterOfUserInstruction.equals("C")) { //if the letter is C...
                    manipulatedMessage = ""; //clear manipulated message because it will now change based on the new instruction given by the letter
                    while (!lettersArray.isEmpty()) { //while the Stack is not empty...
                        if (lettersArray.top().equals(userInstruction.substring(j + 1, j + 2))) //if the letter at the top of the Stack is equivalent to the letter we want to change...
                            manipulatedMessage += userInstruction.substring(j + 2, j + 3); //then replace that letter with the one of the user's choosing
                        else
                            manipulatedMessage += lettersArray.top(); //if not the case, then simply keep the letter at the top as usual
                        lettersArray.pop(); //remove the top element from the Stack so we can get the next letter in the message
                    }
                    for (int i = maxIndex - 1; i >= 0; i--) //for each letter in the message...
                        lettersArray.push(manipulatedMessage.substring(i, i + 1)); //add the letter back into the Stack
                    j += 2; //skip two letters because we already accounted for them in this if statement
                }
            }
            return manipulatedMessage; //return the complete edited message
        }
    }