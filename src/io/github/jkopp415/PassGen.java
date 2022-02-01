package io.github.jkopp415;

import java.util.concurrent.ThreadLocalRandom;

public class PassGen
{
    // The list of letters that the program can choose from
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    // Returns a random integer between zero and max - 1
    private static int getRandomInt(int max)
    {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

    // Returns a random letter with the possibility of being uppercase
    private static String getRandomLetter(boolean hasUppercase)
    {

        // Pick a random letter from the LETTERS string
        int randNum = getRandomInt(LETTERS.length());
        String letter = LETTERS.substring(randNum, randNum + 1);

        /*
         * If hasUppercase is true, there is a 50% chance that the letter will be
         * converted to uppercase
         */
        if (hasUppercase)
        {
            int randCase = getRandomInt(2);
            return randCase == 0 ? letter : letter.toUpperCase();
        }

        // If hasUppercase is false, just return the letter to the GUI
        return letter;
    }

    /*
     * Returns a random character, either a number or a symbol, depending on
     * the function's input
     */
    private static String getRandomCharacter(String charList)
    {

        // Return a blank string if no characters are given in charList
        if (charList.length() == 0) return "";

        // If there are characters given, pick a random character from the list
        int randNum = getRandomInt(charList.length());
        return charList.substring(randNum, randNum + 1);
    }

    // Generates a password based on constraints taken from the GUI
    public static String generatePassword(int passLen, boolean hasUppercase,
                                          String numbers, String symbols)
    {

        // Create a new StringBuilder to hold the password
        StringBuilder password = new StringBuilder();

        // Loop a total of passLen times
        for (int i = 0; i < passLen; i++)
        {
            // Get a random number from 0 to 4 (inclusive)
            int randNum = getRandomInt(5);

            /*
             * There is a 20% chance that the next character will be a number, a 20%
             * chance that the next character will be a symbol, and a 60% chance that
             * the next character will be either an upper or lowercase letter
             */
            if (randNum == 0 && numbers.length() > 0)
                password.append(getRandomCharacter(numbers));
            else if (randNum == 1 && symbols.length() > 0)
                password.append(getRandomCharacter(symbols));
            else
                password.append(getRandomLetter(hasUppercase));
        }

        // Convert the password builder to a string and return it to the GUI
        return password.toString();
    }
}
