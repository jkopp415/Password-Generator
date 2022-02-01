package io.github.jkopp415;

import javax.swing.*;
import java.awt.*;

public class PassGenUI
{
    public static void createGUI()
    {
        // Set the window's look and feel to that of the system's operating system
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException |
                ClassNotFoundException |
                InstantiationException |
                IllegalAccessException e)
        {
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
        passGenButton.addActionListener(e ->
        {
            /*
             * Check if an item in the password length combo box is selected,
             * and if so set that as the passLen variable
             */
            Integer passLen = (Integer) passLenCombo.getSelectedItem();
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
            String password = PassGen.generatePassword(
                    passLen, hasUpper, numList, symbList
            );
            passDispField.setText(password);
        });

        // Set the frame's settings and display it
        frame.setSize(425, 375);
        frame.setTitle("Password Generator");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        createGUI();
    }
}
