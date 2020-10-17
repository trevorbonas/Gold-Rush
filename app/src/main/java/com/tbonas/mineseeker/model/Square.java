package com.tbonas.mineseeker.model;

public class Square {
    Boolean clicked;
    Boolean gold;
    Boolean goldClicked;
    int goldNearby;

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
