package com.tbonas.mineseeker.model;

import com.tbonas.mineseeker.MainActivity;
import com.tbonas.mineseeker.OptionsActivity;

import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The logic behind the mine the player interacts with, mine as in a gold mine.
 *
 * Keeps an ArrayList of mine squares and keep tracks of the state of each square,
 * the number of gold present, the rows and columns, and provides many getters and
 * setters and well as methods for resizing, clearing, and initializing.
 * There is some clunkiness with accessing the Square classes in the ArrayList,
 * if this were written in C++ I would have the Square be a struct, unfortunately
 * here every change in state of the mine requires calling Square getters and setters,
 * making things more complicated.
 */
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
        numGold = 6;
        rows = 4;
        columns = 6;
        init();
    }

    // Adds column * rows number of Squares to squares
    // Called upon app boot
    public void init() {
        if (columns < 0 || rows < 0) {
            return;
        }
        for (int i = 0; i < columns; i++) {
            squares.add(new ArrayList<Square>());
            for (int j = 0; j < rows; j++) {
                squares.get(i).add(new Square());
            }
        }

    }

    // Deletes or adds rows/columns
    public void resize(int columns, int rows){
        if (this.columns <= 0 || this.rows <= 0) {
            return;
        }
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

    public void setNumGold(int x) throws InvalidParameterException {
        if (x < 0 || x > columns * rows) {
            throw new InvalidParameterException("Input gold number is invalid");
        }
        numGold = x;
    }

    // Adds a random amount of mines to the squares
    // and updates all neighbours' nearby value by +1
    // Called when Play Game button is pressed
    public void putGold() {
        if (this.numGold <= 0) {
            return;
        }
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
        if (columns < 0 || rows < 0) {
            return;
        }
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
