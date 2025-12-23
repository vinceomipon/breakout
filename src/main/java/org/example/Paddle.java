package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a paddle in a breakout game. The client can choose to set its
 * x and y coordinate, speed and size, or choose not to pass any values and
 * thus rely on the default values instead.
 */
public class Paddle {
    private int paddleX;
    private int paddleY;
    private int paddleSpeed;
    private final int paddleWidth;
    private final int paddleHeight;

    public static final int MAX_WIDTH = 500;
    public static final int MAX_HEIGHT = 500;

    /**
     * Creates a paddle object defined by its x, y coordinates as well as
     * its horizontal speed, and size set by the user
     * @param x the x coordinate of the paddle
     * @param y the y coordinate of the paddle
     * @param speed the speed of the paddle
     * @param width the width of the paddle
     * @param height the height of the paddle
     */
    public Paddle(int x, int y, int speed, int width, int height) {
        paddleX = x;
        paddleY = y;
        paddleSpeed = speed;
        paddleWidth = width;
        paddleHeight = height;
    }

    public Paddle(int width, int height) {
        this.paddleX = 250;
        this.paddleY = 450;
        this.paddleSpeed = 1;
        this.paddleWidth = width;
        this.paddleHeight = height;
    }

    public int getPaddleX() {
        return paddleX;
    }
    public int getPaddleY() {
        return paddleY;
    }
    public int getPaddleSpeed() {
        return paddleSpeed;
    }
    public int getPaddleWidth() {
        return paddleWidth;
    }
    public int getPaddleHeight() {
        return paddleHeight;
    }

    public void moveLeft() {
        if (paddleX - paddleSpeed > 0) {
            paddleX -= paddleSpeed;
            System.out.println("Paddle X: " + paddleX);
        }
    }

    public void moveRight() {
        if (paddleX + paddleWidth + paddleSpeed < MAX_WIDTH) {
            paddleX += paddleSpeed;
            System.out.println("Paddle X: " + paddleX);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
    }
}
