package org.example;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BrickLayout {

    private final HashMap<Point,Boolean> brickMap;
    private final int brickWidth;
    private final int brickHeight;
    private final int rows;
    private final int cols;

    // Constants for window
    public static final int MAX_WIDTH = 500;
    public static final int MAX_HEIGHT = 500;

    public BrickLayout(int row, int col) {
        this.brickMap = new HashMap<>();
        this.rows = row;
        this.cols = col;

        // Initially sets all bricks in the layout to true
        initializeMap( 0);
        this.brickWidth = (MAX_WIDTH - 40) / col;
        this.brickHeight = 150 / row;


    }

    private boolean initializeMap(int index) {
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

        return initializeMap( index + 1);
    }


    public void draw(Graphics2D g) {
        recursiveDraw(g, 0);
    }

    private boolean recursiveDraw(Graphics2D g,int index) {
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


        return recursiveDraw(g,index + 1);
    }

    // Immutable getter methods

    public int getBrickHeight() {
        return brickHeight;
    }
    public int getBrickWidth() {
        return brickWidth;
    }
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public Map<Point, Boolean> getBrickMap() {
        return Collections.unmodifiableMap(this.brickMap);
    }
}
