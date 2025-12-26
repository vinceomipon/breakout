package org.example;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BrickLayout {

    private final HashMap<Point, Boolean> brickMap;
    private final int brickWidth;
    private final int brickHeight;
    private final int rows;
    private final int cols;

    // Constants for window
    public static final int MAX_WIDTH = 500;
    public static final int MAX_HEIGHT = 500;

    /**
     * Constructs a BrickLayout with the specified number of rows and columns.
     * Initializes all bricks as active (not broken).
     *
     * @param row the number of rows of bricks
     * @param col the number of columns of bricks
     */
    public BrickLayout(int row, int col) {
        this.brickMap = new HashMap<>();
        this.rows = row;
        this.cols = col;

        // Initially sets all bricks in the layout to true
        initializeMap(0);
        this.brickWidth = (MAX_WIDTH - 40) / col;
        this.brickHeight = 150 / row;


    }

    /**
     * Recursively initializes all bricks in the layout to true (active).
     *
     * @param index the current 1D index being initialized
     * @return false when all bricks have been initialized
     */
    public boolean initializeMap(int index) {
        // Handling base case
        // If the 1D index is outside the 2D bounds
        if (index >= this.rows * this.cols) {
            return false;
        }

        // Extract the row and col values from the index
        int rowIndex = index / this.cols;
        int colIndex = index % this.cols;
        Point point = new Point(colIndex, rowIndex);
        this.brickMap.put(point, true);

        return initializeMap(index + 1);
    }

    /**
     * Draws all active bricks in the layout.
     *
     * @param g the Graphics2D context used for drawing
     */
    public void draw(Graphics2D g) {
        recursiveDraw(g, 0);
    }

    /**
     * Recursively draws each brick that is still active (not broken).
     *
     * @param g the Graphics2D context used for drawing
     * @param index the current 1D index being drawn
     * @return false when all bricks have been processed
     */
    private boolean recursiveDraw(Graphics2D g, int index) {
        int rowIndex = index / this.cols;
        int colIndex = index % this.cols;
        Point point = new Point(colIndex, rowIndex);

        // Base case if out of bounds or brick  has already collided
        if (index >= this.rows * this.cols) {
            return false;
        }

        // Recursive step,
        if (this.brickMap.get(point)) {
            g.setColor(Color.white);
            g.fillRect(colIndex * brickWidth + 20, rowIndex * brickHeight + 20, brickWidth, brickHeight);

            // Draw the borders to separate bricks
            g.setStroke(new BasicStroke(3));
            g.setColor(Color.black);
            g.drawRect(colIndex * brickWidth + 20, rowIndex * brickHeight + 20, brickWidth, brickHeight);
        }


        return recursiveDraw(g, index + 1);
    }

    // Immutable getter methods

    /**
     * Gets the height of each brick.
     *
     * @return the brick height in pixels
     */
    public int getBrickHeight() {
        return brickHeight;
    }

    /**
     * Gets the width of each brick.
     *
     * @return the brick width in pixels
     */
    public int getBrickWidth() {
        return brickWidth;
    }

    /**
     * Gets an unmodifiable view of the brick map.
     *
     * @return immutable map of brick positions to their active state
     */
    public Map<Point, Boolean> getBrickMap() {
        return Collections.unmodifiableMap(this.brickMap);
    }

    // Mutating functions

    /**
     * Sets the specified entry of the map to false which
     * signifies when a collision occurs
     * @param point The key to search for in the map
     * @return true if object contains point and is set to false, false otherwise
     */
    public boolean updateBrickEntry(Point point) {
        if (this.brickMap.containsKey(point)) {
            this.brickMap.put(point, false);
            return true;
        }

        return false;
    }

    /**
     * Checks if all bricks have been cleared (broken).
     *
     * @return true if all bricks are broken, false otherwise
     */
    public boolean bricksCleared() {
        return this.brickMap.values().stream().
                noneMatch(b -> b);
    }
}
