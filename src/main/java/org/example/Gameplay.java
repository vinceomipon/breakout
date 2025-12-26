package org.example;



import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.stream.Collectors;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    // Concrete Rep
    private final Ball ball;
    private final Paddle paddle;
    private final BrickLayout bricks;
    private javax.swing.Timer timer;
    private boolean moveLeft;
    private boolean moveRight;
    private int lives;
    private int score;

    private boolean gameOver;
    private boolean gameWon;

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
        int paddleHeight = 8;
        int paddleWidth = 100;
        this.paddle = new Paddle(paddleWidth, paddleHeight);

        // Create breakLayout Object
        int rows = 1;
        int col = 1;
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
        this.gameWon = false;

        this.lives = 3;

        this.score = 0;

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

        g.drawString("Score: " + this.score, 450, 10);

        // if the game is over
        if (gameOver) {
            handleGameOver(g);
        }

        if (gameWon) {
            handleGameWon(g);
        }
    }

    /**
     * Displays "GAME WON" message with restart instruction.
     *
     * @param g the Graphics context used for drawing
     */
    private void handleGameWon(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME WON", 125, 200);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Press enter to continue", 200, 300);
    }

    /**
     * Displays "GAME OVER" message with restart instruction.
     *
     * @param g the Graphics context used for drawing
     */
    private void handleGameOver(Graphics g) {
        // Clear the screen and print game over
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", 125, 200);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Press enter to continue", 200, 300);

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
            ball.setBallSpeedX(0);
            ball.setBallSpeedY(0);
            return;
        }

        if (moveLeft) {
            paddle.moveLeft();
        }
        if (moveRight) {
            paddle.moveRight();
        }


        boolean outOfBounds = ball.moveBall();
        this.collisionDetection();

        if (outOfBounds) {
            handleLives();
        }

        boolean allBricksCleared = this.bricks.bricksCleared();

        if (allBricksCleared) {
            this.gameWon = true;
        }



        repaint();
    }

    /**
     * Checks for collisions between ball and paddle or bricks.
     *
     * @return true if any collision was detected, false otherwise
     */
    public boolean collisionDetection() {

        return brickCollision() || paddleCollision();
    }

    /**
     * Checks if ball hit the paddle and bounces it upward.
     *
     * @return true if collision occurred, false otherwise
     */
    private boolean paddleCollision() {
        int ballX = ball.getBallX();
        int ballY = ball.getBallY();
        int ballSize = ball.getBallSize();
        int paddleX = paddle.getPaddleX();
        int paddleY = paddle.getPaddleY();
        int paddleWidth = paddle.getPaddleWidth();
        int paddleHeight = paddle.getPaddleHeight();
        Rectangle ballRect = new Rectangle(ballX, ballY, ballSize, ballSize);
        Rectangle paddleRect = new Rectangle(paddleX, paddleY, paddleWidth, paddleHeight);

        // if ball intersects with paddle
        if (ballRect.intersects(paddleRect)) {
            System.out.println("Collision Detected");
            ball.setBallSpeedY(ball.getBallSpeedY() * -1);
            ball.setBallY(paddleY - ball.getBallSize());
            return true;
        }


        return false;
    }

    /**
     * Checks if ball hit any brick, removes the brick, and increments score.
     *
     * @return true if collision with any brick occurred, false otherwise
     */
    private boolean brickCollision() {
        int ballX = ball.getBallX();
        int ballY = ball.getBallY();
        int ballSize = ball.getBallSize();
        Rectangle ballRect = new Rectangle(ballX, ballY, ballSize, ballSize);
        Map<Point, Boolean> brickGrid = this.bricks.getBrickMap();
        int brickWidth = this.bricks.getBrickWidth();
        int brickHeight = this.bricks.getBrickHeight();

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
        this.bricks.updateBrickEntry(collisionBlock.getKey());

        ++this.score;
        repaint();
        return true;

    }

    /**
     * Reverses ball velocity based on which side of the brick was hit.
     *
     * @param ballRect the Rectangle representing the ball's bounds
     * @param brick the Rectangle representing the brick's bounds
     */
    private void handleBounce(Rectangle ballRect, Rectangle brick) {
        // Intersection tells us where the ball intersected with the brick
        Rectangle intersection = ballRect.intersection(brick);

        if (intersection.width < intersection.height) {
            // Side collision detected
            this.ball.setBallSpeedX(this.ball.getBallSpeedX() * -1);

        } else {
            // else top or bottom collision detected
            this.ball.setBallSpeedY(this.ball.getBallSpeedY() * -1);
        }

    }

    // Helper method to handle the logic after user reaches out of bounds
    private void handleLives() {
        this.lives--;

        // If lives is negative set game over to true
        if (lives <= 0) {
            gameOver = true;
        } else {
            // resets ball position to default position
            ball.defaultBallPosition();
            ball.setBallSpeedX(2);
            ball.setBallSpeedY(2);
            paddle.resetPaddlePosition();
        }
    }

    // Resets the game when the users presses enter on keyboard
    private void resetGame() {

        // re-initializes the variables
        this.gameOver = false;
        this.gameWon = false;
        this.score = 0;
        this.lives = 3;
        ball.defaultBallPosition();
        ball.setBallSpeedX(2);
        ball.setBallSpeedY(2);
        bricks.initializeMap(0);
        paddle.resetPaddlePosition();

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
        if (e.getKeyCode() == KeyEvent.VK_R && (gameOver || gameWon)) {
            resetGame();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
