package org.example;

import java.awt.*;
import java.util.HashMap;

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
        this.brickWidth = MAX_WIDTH / col;
        this.brickHeight = MAX_HEIGHT / row;





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

        // Base case if out of bounds or brick is has already collided
        if (index >= this.rows * this.cols || !this.brickMap.get(point)) {
            return false;
        }

        // Recursive step,
        g.setColor(Color.white);
        g.fillRect(rowIndex * brickWidth + 20, colIndex * brickHeight + 20, brickWidth, brickHeight);

        return recursiveDraw(g,index + 1);

    }
}
