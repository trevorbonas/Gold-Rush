package com.tbonas.mineseeker.model;

import java.security.InvalidParameterException;

/**
 * The square the user interacts with.
 *
 * Holds four private variables, whether or not the given square has been clicked,
 * whether it holds gold, the number of gold bars in its row and column, and whether
 * it is a gold bar that has already been revealed and is being clicked again, to do
 * another scan.
 */
public class Square {
    private Boolean clicked;
    private Boolean gold;
    private Boolean goldClicked;
    private int goldNearby;

    public Square() {
        gold = false;
        clicked = false;
        goldNearby = 0;
    }

    public void setGold(Boolean mine) {
        this.gold = mine;
    }

    public void setGoldNearby(int number) throws InvalidParameterException {
        if (number < 0) {
            throw new InvalidParameterException("Square input gold nearby < 0");
        }

        this.goldNearby = number;
    }

    public int getGoldNearby() {
        return this.goldNearby;
    }

    public void addGoldNearby(int n) {
        if ((this.goldNearby + n) < 0) {
            throw new InvalidParameterException("Input number makes goldnearby negative");
        }

        this.goldNearby += n;
    }

    public void setClicked(Boolean setting) {
        this.clicked = setting;
    }

    public void setGoldClicked(Boolean setting) {
        this.goldClicked = setting;
    }

    public Boolean wasClicked() {
        return this.clicked;
    }

    public Boolean wasGoldClicked() {
        return this.goldClicked;
    }

    public Boolean isGold() {
        return this.gold;
    }
}
