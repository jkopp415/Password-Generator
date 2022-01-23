package main;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class PasswordGenerator {

    /**
     * The list of letters that the program can choose from.
     */
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Returns a random integer.
     * @param max One plus the maximum number that {@link ThreadLocalRandom} may return.
     * @return A random integer between zero and ({@code max} - 1).
     */
    private static int getRandomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

    /**
     * Returns a random letter, with the possibility of being uppercase.
     * @param hasUppercase Determines if it should be possible for the letter to be uppercase.
     * @return A letter that is either uppercase or lowercase.
     */
    private static String getRandomLetter(boolean hasUppercase) {

        // Pick a random letter from the LETTERS string
        int randNum = getRandomInt(LETTERS.length());
        String letter = LETTERS.substring(randNum, randNum + 1);

        /*
         * If hasUppercase is true, there is a 50% chance that the letter will be
         * converted to uppercase
         */
        if (hasUppercase) {
            int randCase = getRandomInt(2);
            return randCase == 0 ? letter : letter.toUpperCase();
        }

        // If hasUppercase is false, just return the letter to the GUI
        return letter;
    }

    /**
     * Returns a random character, either a number or symbol, depending on the function input.
     * @param charList The String of numbers or symbols.
     * @return A random character from the {@code charList} string.
     */
    private static String getRandomCharacter(String charList) {

        // Return a blank string if no characters are given in charList
        if (charList.length() == 0) return "";

        // If there are characters given, pick a random character from the list
        int randNum = getRandomInt(charList.length());
        return charList.substring(randNum, randNum + 1);
    }

    /**
     * Generates a password based on constraints taken from the GUI.
     * @param passLen The length of the password
     * @param hasUppercase Whether the password can contain uppercase letters or not.
     * @param numbers The list of numbers that are available for the password.
     * @param symbols The list of symbols that are available for the password.
     * @return A password of {@code passLen} length, with a mix of uppercase and lowercase letters
     * (depending on the value of {@code hasUppercase}), numbers from the {@code numbers} string,
     * and symbols from the {@code symbols} string.
     */
    private static String generatePassword(int passLen, boolean hasUppercase, String numbers, String symbols) {

        // Create a new StringBuilder to hold the password
        StringBuilder password = new StringBuilder();

        // Loop a total of passLen times
        for (int i = 0; i < passLen; i++) {
            // Get a random number from 0 to 4 (inclusive)
            int randNum = getRandomInt(5);

            /*
             * There is a 20% chance that the next character will be a number, a 20%
             * chance that the next character will be a symbol, and a 60% chance that
             * the next character will be either an upper or lowercase letter
             */
            if (randNum == 0)
                password.append(getRandomCharacter(numbers));
            else if (randNum == 1)
                password.append(getRandomCharacter(symbols));
            else
                password.append(getRandomLetter(hasUppercase));
        }

        // Convert the password builder to a string and return it to the GUI
        return password.toString();
    }

    public static void main(String[] args) {

        // Set the window's look and feel to that of the system's operating system
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException |
                ClassNotFoundException |
                InstantiationException |
                IllegalAccessException e) {
            throw new RuntimeException("idk i think something went wrong with the UI");
        }

        // Create fonts
        Font bigText = new Font("Tahoma", Font.PLAIN, 24);
        Font smallText = new Font("Tahoma", Font.PLAIN, 14);
        Font smallerText = new Font("Tahoma", Font.PLAIN, 12);

        // Create the frame object
        JFrame frame = new JFrame();

        // Create a GridBagLayout and add it to the main panel
        GridBagLayout grid = new GridBagLayout();
        frame.setLayout(grid);
        GridBagConstraints mainGbc = new GridBagConstraints();

        // ---------------------------------------------------------

        // Create the password generation button
        JButton passGenButton = new JButton("Generate Password");
        passGenButton.setFont(bigText);
        passGenButton.setFocusable(false);
        // Add the password generation button to the main panel
        mainGbc.fill = GridBagConstraints.HORIZONTAL;
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.ipadx = 75;
        mainGbc.ipady = 25;
        mainGbc.insets = new Insets(5, 0, 5, 0);
        frame.add(passGenButton, mainGbc);

        // Create the password display field
        JTextField passDispField = new JTextField();
        passDispField.setEditable(false);
        passDispField.setHorizontalAlignment(SwingConstants.CENTER);
        passDispField.setFont(bigText);
        // Add the password display field to the main panel
        mainGbc.gridx = 0;
        mainGbc.gridy = 1;
        frame.add(passDispField, mainGbc);

        // Create option panel and assign it the GridBagLayout
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(grid);
        GridBagConstraints optionGbc = new GridBagConstraints();

        // Create the password length label
        JLabel passLenLabel = new JLabel("Password Length");
        passLenLabel.setFont(smallText);
        // Add the password length label to the option panel
        optionGbc.gridx = 0;
        optionGbc.gridy = 0;
        optionGbc.insets = new Insets(0, 0, 0, 30);
        optionPanel.add(passLenLabel, optionGbc);

        // Create the has uppercase label
        JLabel hasUpperLabel = new JLabel("Has Uppercase?");
        hasUpperLabel.setFont(smallText);
        // Add the has uppercase label to the option panel
        optionGbc.gridx = 1;
        optionGbc.gridy = 0;
        optionGbc.insets = new Insets(0, 30, 0, 0);
        optionPanel.add(hasUpperLabel, optionGbc);

        // Create the password length combo box
        Integer[] passLenList = {8, 9, 10, 11, 12, 13, 14, 15};
        JComboBox<Integer> passLenCombo = new JComboBox<>(passLenList);
        passLenCombo.setSelectedIndex(4);
        passLenCombo.setFont(smallerText);
        // Add the password length combo box to the option panel
        optionGbc.gridx = 0;
        optionGbc.gridy = 1;
        optionGbc.insets = new Insets(5, 0, 0, 30);
        optionPanel.add(passLenCombo, optionGbc);

        // Create the has uppercase checkbox
        JCheckBox hasUpperCheck = new JCheckBox("", true);
        // Add the has uppercase checkbox to the option panel
        optionGbc.gridx = 1;
        optionGbc.gridy = 1;
        optionGbc.insets = new Insets(5, 30, 0, 0);
        optionPanel.add(hasUpperCheck, optionGbc);

        // Create the numbers label
        JLabel numsLabel = new JLabel("Numbers");
        numsLabel.setFont(smallText);
        // Add the numbers label to the option panel
        optionGbc.gridx = 0;
        optionGbc.gridy = 2;
        optionGbc.insets = new Insets(20, 0, 0, 30);
        optionPanel.add(numsLabel, optionGbc);

        // Create the symbols label
        JLabel symbLabel = new JLabel("Special Characters");
        symbLabel.setFont(smallText);
        // Add the symbols label to the option panel
        optionGbc.gridx = 1;
        optionGbc.gridy = 2;
        optionGbc.insets = new Insets(20, 30, 0, 0);
        optionPanel.add(symbLabel, optionGbc);

        // Create the numbers field
        JTextField numsField = new JTextField("1234567890");
        numsField.setHorizontalAlignment(SwingConstants.CENTER);
        numsField.setFont(smallerText);
        // Add the numbers field to the option panel
        optionGbc.gridx = 0;
        optionGbc.gridy = 3;
        optionGbc.ipadx = 10;
        optionGbc.insets = new Insets(5, 0, 0, 30);
        optionPanel.add(numsField, optionGbc);

        // Create the symbols field
        JTextField symbField = new JTextField("!@#$%&");
        symbField.setHorizontalAlignment(SwingConstants.CENTER);
        symbField.setFont(smallerText);
        // Add the symbols field to the option panel
        optionGbc.gridx = 1;
        optionGbc.gridy = 3;
        optionGbc.ipadx = 10;
        optionGbc.insets = new Insets(5, 30, 0, 0);
        optionPanel.add(symbField, optionGbc);

        // Add the option panel to the main panel
        mainGbc.gridx = 0;
        mainGbc.gridy = 2;
        frame.add(optionPanel, mainGbc);

        // ---------------------------------------------------------

        // Create an action listener for the password generation button
        passGenButton.addActionListener(e -> {

            /*
             * Check if an item in the password length combo box is selected,
             * and if so set that as the passLen variable
             */
            Integer passLen = (Integer)passLenCombo.getSelectedItem();
            if (passLen == null)
                passLen = 12;

            // Get the other three variables from their respective GUI components
            boolean hasUpper = hasUpperCheck.isSelected();
            String numList = numsField.getText();
            String symbList = symbField.getText();

            /*
             * Generate a password from the four above variables, and set it as the
             * text in the password display field
             */
            String password = generatePassword(passLen, hasUpper, numList, symbList);
            passDispField.setText(password);
        });

        // Set the frame's settings and display it
        frame.setSize(425, 375);
        frame.setTitle("Password Generator");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
