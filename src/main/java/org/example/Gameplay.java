package org.example;



import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    // Concrete Rep
    private final Ball ball;
    private final Paddle paddle;
    private final BrickLayout bricks;
    private javax.swing.Timer timer;
    private boolean moveLeft;
    private boolean moveRight;
    private int lives;

    private boolean gameOver;

    // Constants to be used
    public static final int MAX_WIDTH = 500;
    public static final int MAX_HEIGHT = 500;

    /**
     * Constructs a Gameplay panel.
     * Initializes ball and paddle objects and allows this object
     * to receive keyboard input
     * Creates a timer that drives the game loop
     */
    public Gameplay() {
        // Paints the background back
        this.setBackground(Color.BLACK);
        // Creates a ball object
        this.ball = new Ball();

        // Creates a paddle object
        int PADDLE_HEIGHT = 8;
        int PADDLE_WIDTH = 100;
        this.paddle = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT);

        // Create breakLayout Object
        int rows = 8;
        int col = 12;
        this.bricks = new BrickLayout(rows, col);

        // This object will receive keyboard input
        setFocusable(true);

        // Creates an event handler to deal with keyboard input
        addKeyListener(this);

        // Set both flags to false
        this.moveLeft = false;
        this.moveRight = false;

        // Set game over to false
        this.gameOver = false;

        this.lives = 3;

        // Set-up an event-driven timer to receive inputs
        // it fires every 10 ms
        this.timer = new Timer(10,  this);
        timer.start();
    }

    /**
     * Paints the current game state by clearing the screen
     * redraws the ball, paddle and game border
     *
     * @param g the Grahpics context used for drawing
     */
    @Override
    public void paintComponent(Graphics g) {
        // Clears the screen
        super.paintComponent(g);
        // Draws the ball
        ball.draw(g);
        // Draws the paddle
        paddle.draw(g);

        // Draws the borders of the screen
        drawBorder(g);

        // Paints the bricklayout
        Graphics2D g2d = (Graphics2D) g;
        bricks.draw(g2d);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospace", Font.BOLD, 10));
        g.drawString("Lives: " + this.lives, 10, 10);

        // if the game is over
        if (gameOver) {
            handleGameOver(g);
        }
    }

    private void handleGameOver(Graphics g) {
        // Clear the screen and print game over
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", 200, 200);

    }

    /**
     * Draws a rectangular border around the playable screen
     *
     * @param g the Graphics context used for drawing
     */
    private void drawBorder(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(0, 0, MAX_WIDTH - 1, MAX_HEIGHT - 1);
    }

    /**
     * Handles the event everytime a timer is fired for input.
     * Moves the paddle based on the user's keyboard input,
     * checks for ball-paddle collision, updates ball movement
     * and repaints the screen.
     *
     * @param e the ActionEvent triggered by the timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return;
        }

        if (moveLeft) {
            paddle.moveLeft();
        }
        if (moveRight) {
            paddle.moveRight();
        }

        ball.collisionDetection(paddle, bricks);
        boolean outOfBounds = ball.moveBall();

        if (outOfBounds) {
            handleLives();
        }

        repaint();
    }

    // Helper method to handle the logic after user reaches out of bounds
    private void handleLives() {
        this.lives--;

        // If lives is negative set game over to true
        if (lives <= 0) {
            gameOver = true;
        } else {
            // resets ball position to default position
            ball.setBallPosition();
        }
    }

    /**
     * Handles key press events. Sets movement flags when 'A' or 'D' is pressed
     * @param e the keyEvent representing the pressed key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            moveLeft = true;
            System.out.println("Key pressed: " + e.getKeyCode());
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            moveRight = true;
            System.out.println("Key pressed: " + e.getKeyCode());
        }
    }

    /**
     * Handles key release events. Clears movement flags when 'A' or 'D'
     * is released.
     * @param e the keyEvent representing the released key
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            moveLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            moveRight = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
