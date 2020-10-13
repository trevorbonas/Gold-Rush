package com.tbonas.mineseeker.model;

public class Square {
    Boolean clicked;
    Boolean mine;
    int minesNearby;
    public void Square() {
        mine = false;
        clicked = false;
        minesNearby = 0;
    }

    public void setMine(Boolean mine) {
        this.mine = mine;
    }

    public void setMinesNearby(int number) {
        minesNearby = number;
    }

    public int getMinesNearby() {
        return minesNearby;
    }

    public void addMinesNearby(int n) {
        minesNearby += n;
    }

    public void setClicked(Boolean setting) {
        clicked = setting;
    }
}
