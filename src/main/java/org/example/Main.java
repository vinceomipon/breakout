package org.example;

import org.example.Gameplay;

import javax.swing.*;
import java.awt.*;


// Driver Class
public class Main {
    // main function
    // Constants for window
    public static final int MAX_WIDTH = 500;
    public static final int MAX_HEIGHT = 500;

    public static void main(String[] args)
    {
        // Create a JFrame object
        JFrame frame = new JFrame("Breakout");

        // Set the size of JFrame
        frame.setSize(MAX_WIDTH,MAX_HEIGHT);

        // Close the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Don't allow for resize
        frame.setResizable(true);

        Gameplay gp = new Gameplay();
        frame.add(gp);

        frame.setVisible(true);
    }
}