package com.tbonas.mineseeker.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

// The logic behind the mine the player interacts with, mine as in a gold mine
public class Mine {
    int numGold; // Total number of gold ores on the mine field
    private int rows;
    private int columns;
    private ArrayList<ArrayList<Square>> squares = new ArrayList<>();
    private static Mine instance; // Singleton instance of MineField

    public static Mine getInstance() {
        if (instance == null) {
            instance = new Mine();
        }
        return instance;
    }

    private Mine() {
        // Private for singleton use
        rows = 4; // Default, starting values upon app boot
        columns = 7;
        numGold = 6;
        init();
    }

    // Adds column * rows number of Squares to squares
    // Called upon app boot
    public void init() {
        for (int i = 0; i < columns; i++) {
            squares.add(new ArrayList<Square>());
            for (int j = 0; j < rows; j++) {
                squares.get(i).add(new Square());
            }
        }

    }

    // Deletes or adds rows/columns
    public void resize(int columns, int rows) {
        if (rows < this.rows) {
            for (int i = 0; i < this.columns; i++) {
                for (int j = this.rows - 1; j >= rows; j--) {
                    squares.get(i).remove(j);
                }
            }
        }
        else if (rows > this.rows) {
            for (int i = 0; i < this.columns; i++) {
                for (int j = this.rows - 1; j < rows; j++) {
                    squares.get(i).add(new Square());
                }
            }
        }

        if (columns < this.columns) {
            for (int i = this.columns - 1; i >= columns; i--) {
                squares.remove(i);
            }
        }
        else if (columns > this.columns) {
            for (int i = this.columns - 1; i < columns; i++) {
                squares.add(new ArrayList<Square>());
                for (int j = 0; j < rows; j++) {
                    squares.get(i).add(new Square());
                }
            }
        }
        this.rows = rows;
        this.columns = columns;
    }

    public void setNumMines(int x) {
        if (x < 0 || x > columns * rows) {
            throw new InvalidParameterException();
        }
        numGold = x;
    }

    // Adds a random amount of mines to the squares
    // and updates all neighbours' nearby value by +1
    // Called when Play Game button is pressed
    public void putGold() {
        int x;
        int y;
        Random rnd = new Random();

        // Add a random amount of mines to the field in range 3 to (rows*columns - 4)
        for (int i = 0; i < numGold; i++) {
            // Put gold in random position in mine
            // While loop ensures the same square isn't
            // set to gold twice
            x = rnd.nextInt(columns);
            y = rnd.nextInt(rows);
            while (squares.get(x).get(y).isGold()) {
                x = rnd.nextInt(columns);
                y = rnd.nextInt(rows);
            }
            squares.get(x).get(y).setGold(true);

            // Update all 'mineNearby' values in the mine's row and column
            for (int j = 0; j < columns; j++) {
                if (j != x) {
                    squares.get(j).get(y).addGoldNearby(1);
                }
            }
            for (int k = 0; k < rows; k++) {
                if (k != y) {
                    squares.get(x).get(k).addGoldNearby(1);
                }
            }
        }
    }

    public int getNearby(int x, int y) {
        return squares.get(x).get(y).getGoldNearby();
    }

    // When a mine is found this will minus 1 from all neighbour
    // squares' minesNearby value
    public void updateMinesNearby(int x, int y) {
        for (int j = 0; j < columns; j++) {
            if (j != x) {
                squares.get(j).get(y).addGoldNearby(-1);
            }
        }
        for (int k = 0; k < rows; k++) {
            if (k != y) {
                squares.get(x).get(k).addGoldNearby(-1);
            }
        }
    }

    public void clear() {
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                squares.get(i).get(j).setClicked(false);
                squares.get(i).get(j).setGoldClicked(false);
                squares.get(i).get(j).setGold(false);
                squares.get(i).get(j).setGoldNearby(0);
            }
        }
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public Boolean goldFound(int x, int y) {
        return squares.get(x).get(y).isGold();
    }

    public Boolean squareClicked(int x, int y) {
        return squares.get(x).get(y).wasClicked();
    }

    public Boolean squareGoldClicked(int x, int y) {
        return squares.get(x).get(y).wasGoldClicked();
    }

    public void setSquareClicked(Boolean condition, int x, int y) {
        squares.get(x).get(y).setClicked(condition);
    }

    public void setSquareGoldClicked(Boolean condition, int x, int y) {
        squares.get(x).get(y).setGoldClicked(condition);
    }

    public int getNumGold() {
        return this.numGold;
    }
}
