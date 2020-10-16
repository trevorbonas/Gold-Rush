package com.tbonas.mineseeker.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineField {
    int numMines; // Total number of mines on the mine field
    private int rows;
    private int columns;
    private ArrayList<ArrayList<Square>> squares = new ArrayList<>();
    private static MineField instance; // Singleton instance of MineField

    public static MineField getInstance() {
        if (instance == null) {
            instance = new MineField();
        }
        return instance;
    }

    private MineField() {
        // Private for singleton use
        rows = 4; // Default, starting values upon app boot
        columns = 7;
        numMines = 5;
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
        numMines = x;
    }

    // Adds a random amount of mines to the squares
    // and updates all neighbours' nearby value by +1
    // Called when Play Game button is pressed
    public void putMines() {
        int x;
        int y;
        Random rnd = new Random();

        // Add a random amount of mines to the field in range 3 to (rows*columns - 4)
        for (int i = 0; i < numMines; i++) {
            // Random position on field
            x = rnd.nextInt(columns);
            y = rnd.nextInt(rows);

            // Plant a mine
            squares.get(x).get(y).setMine(true);

            // Update all 'mineNearby' values in the mine's row and column
            for (int j = 0; j < columns; j++) {
                if (j != x) {
                    squares.get(j).get(y).addMinesNearby(1);
                }
            }
            for (int k = 0; k < rows; k++) {
                if (k != y) {
                    squares.get(x).get(k).addMinesNearby(1);
                }
            }
        }
    }

    public int getNearby(int x, int y) {
        return squares.get(x).get(y).getMinesNearby();
    }

    // When a mine is found this will minus 1 from all neighbour
    // squares' minesNearby value
    public void updateMinesNearby(int x, int y) {
        for (int j = 0; j < columns; j++) {
            if (j != x) {
                squares.get(j).get(y).addMinesNearby(-1);
            }
        }
        for (int k = 0; k < rows; k++) {
            if (k != y) {
                squares.get(x).get(k).addMinesNearby(-1);
            }
        }
    }

    public void clear() {
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                squares.get(i).get(j).setClicked(false);
                squares.get(i).get(j).setMine(false);
                squares.get(i).get(j).setMinesNearby(0);
            }
        }
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }
}
