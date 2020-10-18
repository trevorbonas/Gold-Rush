package com.tbonas.mineseeker.model;

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

    public void setGoldNearby(int number) {
        this.goldNearby = number;
    }

    public int getGoldNearby() {
        return this.goldNearby;
    }

    public void addGoldNearby(int n) {
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
