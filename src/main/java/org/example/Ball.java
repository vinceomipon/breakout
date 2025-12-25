package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Ball extends JPanel {

    // These fields specify the ball's position and speed as well as size
    // Since it is 2 d we have respective X and Y coordiantes
    private int ballX;
    private int ballY;
    private int ballSpeedX;
    private int ballSpeedY;
    private int ballSize;

    // Default values to set the fields if client does not set manually
    public static final int DEFAULT_X = 250;
    public static final int DEFAULT_Y = 300;
    public static final int DEFAULT_SPEEDX = 2;
    public static final int DEFAULT_SPEEDY = 2;
    public static final int DEFAULT_BALLSIZE = 20;

    public static final int MAX_WIDTH = 500;
    public static final int MAX_HEIGHT = 500;


    /**
     * Initializes the ball's position speed, size and timer
     * to update the game every 10 milliseconds.
     * @param ballX The x position of the ball must be between 0 <= x <= MAX_WIDTH
     * @param ballY The y position of the ball must be between 0 <= y <= MAX_HEIGHT
     * @param ballSpeedX The x speed of the ball, must be between -10 <= dx <= 10
     * @param ballSpeedY The y speed of the ball, must be between -10 <= dy <= 10
     * @param ballSize The size of the ball, must be greater than 0
     */
    public Ball(int ballX, int ballY, int ballSpeedX, int ballSpeedY, int ballSize) {
        if (ballX > MAX_WIDTH || ballX < 0 || ballY > MAX_HEIGHT || ballY < 0 || ballSize <= 0) {
            throw new IllegalArgumentException();
        }
        if (ballSpeedX < -10 || ballSpeedX > 10 || ballSpeedY < -10 || ballSpeedY > 10) {
            throw new IllegalArgumentException();
        }
        this.ballX = ballX;
        this.ballY = ballY;
        this.ballSpeedX = ballSpeedX;
        this.ballSpeedY = ballSpeedY;
        this.ballSize = ballSize;

        // Timer to update the game very 10 milliseconds
        Timer timer = new Timer(10,e -> {
            moveBall();
            repaint();
        });
        timer.start();
    }

    /**
     * Initializes the ball's position speed, size and timer
     * to update the game every 10 milliseconds
     */
    public Ball() {
        this.ballX = DEFAULT_X;
        this.ballY = DEFAULT_Y;
        this.ballSpeedX = DEFAULT_SPEEDX;
        this.ballSpeedY = DEFAULT_SPEEDY;
        this.ballSize = DEFAULT_BALLSIZE;
        Timer timer = new Timer(10,e -> {
            moveBall();
            repaint();
        });
        timer.start();
    }

    /**
     * Returns the ball's current x position
     * @return the x position of the ball
     */
    public int getBallX() {
        return ballX;
    }

    /**
     * Returns the ball's current y position
     * @return the y position of the ball
     */
    public int getBallY() {
        return ballY;
    }

    /**
     * Returns the ball's current dx speed
     * @return the x speed of the ball
     */
    public int getBallSpeedX() {
        return ballSpeedX;
    }

    /**
     * Returns the ball's current dy position
     * @return the dy position of the ball
     */
    public int getBallSpeedY() {
        return ballSpeedY;
    }

    /**
     * Gets the size of the ball
     * @return the value of the size of the ball
     */
    public int getBallSize() { return ballSize; }

    public void setBallPosition() {
        this.ballX = DEFAULT_X;
        this.ballY = DEFAULT_Y;
    }

    /**
     * Updates the position of the ball based on its current speed
     */
    public boolean moveBall() {
        // Update the ball based on the current direction of the ballSpeed
        ballX += ballSpeedX;
        ballY += ballSpeedY;
        System.out.println("Ball X: " + ballX + " Ball Y: " + ballY);

        // check if ball hit bottom or not
        return edgeCases();

    }

    /**
     * Flips the direction of the ball's speed if it hits a boundary.
     * Specifically, if it hits the x boundaries of the frame, it will
     * invert the ballSpeedX by multiply it by -1. The logic is similar
     * for the y direction
     * @return false if the edge is the bottom edge, true otherwise
     */
    private boolean edgeCases() {

        if (ballX <= 0 || ballX >= MAX_WIDTH) {
            ballSpeedX *= -1;
            return false;
        }

        if (ballY <= 0) {
            ballSpeedY *= -1;
            return false;
        }

        // if past bottom boundary
        if (ballY >= MAX_HEIGHT) {
            return true;
        }

        return false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.ORANGE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillOval(ballX, ballY, ballSize, ballSize);
    }

    public boolean collisionDetection(Paddle paddle, BrickLayout brickLayout) {

        return brickCollision(brickLayout) || paddleCollision(paddle);
    }

    private boolean paddleCollision(Paddle paddle) {
        int paddleX = paddle.getPaddleX();
        int paddleY = paddle.getPaddleY();
        int paddleWidth = paddle.getPaddleWidth();
        int paddleHeight = paddle.getPaddleHeight();
        Rectangle ballRect = new Rectangle(ballX, ballY, ballSize, ballSize);
        Rectangle paddleRect = new Rectangle(paddleX, paddleY, paddleWidth, paddleHeight);

        // if ball intersects with paddle
        if(ballRect
                .intersects(paddleRect)) {
            System.out.println("Collision Detected");
            ballSpeedY = -ballSpeedY;
            ballY = paddleY - DEFAULT_BALLSIZE;
            return true;
        }


        return false;
    }

    private boolean brickCollision(BrickLayout brickLayout) {
        Rectangle ballRect = new Rectangle(ballX, ballY, ballSize, ballSize);
        Map<Point, Boolean> brickGrid = brickLayout.getBrickMap();
        int brickWidth = brickLayout.getBrickWidth();
        int brickHeight = brickLayout.getBrickHeight();

        // Convert the brick stored in a 2d grid to its actual pixel space
        Map<Point, Rectangle> brickMap = brickGrid.entrySet().stream().
                filter(Map.Entry::getValue).
                map(Map.Entry::getKey).
                collect(Collectors.toMap(
                        p -> p,
                        p -> {
                            int brickX = p.x * brickWidth + 20;
                            int brickY = p.y * brickHeight + 20;
                            return new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        }
                ));


        // Check if the ball collided with any paddle
        Map.Entry<Point, Rectangle> collisionBlock = brickMap.entrySet().stream().
                filter(e -> e.getValue().intersects(ballRect)).
                findFirst().
                orElse(null);

        // return null if the ball did not collide with any rectangle
        if (collisionBlock == null) {
            return false;
        }

        // If the ball did collide handle bounce
        handleBounce(ballRect, collisionBlock.getValue());




        // Set the specified brick at point x,y to false
        brickLayout.updateBrickEntry(collisionBlock.getKey());

        repaint();
        return true;

    }

    private void handleBounce(Rectangle ball, Rectangle brick) {
        // Intersection tells us where the ball intersected with the brick
        Rectangle intersection = ball.intersection(brick);

        if (intersection.width < intersection.height) {
            // Side collision detected
            ballSpeedX *= -1;

        } else {
            // else top or bottom collision detected
            ballSpeedY *= -1;
        }

    }


}
