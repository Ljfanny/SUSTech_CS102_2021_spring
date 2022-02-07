package xyz.model;

import xyz.view.*;

public class Square {
    private BoardLocation location;
    private boolean isOpened;
    private boolean isFlag;
    private boolean hasLandMine;
    private byte numberOfLandMine;
    private byte numberOfLandMineRound;
    private boolean isClick;
    private int boxNum = 0;
    private boolean isBox = false;
    private boolean isAiMarked = false;
    private boolean isPlayerMarked = false;

    public boolean isPlayerMarked() {
        return isPlayerMarked;
    }

    public void setPlayerMarked(boolean playerMarked) {
        isPlayerMarked = playerMarked;
    }

    @Override
    public String toString() {
        return "" + this.isOpened + " " + this.isFlag + " " + this.hasLandMine + " " + this.numberOfLandMine + " " + this.numberOfLandMineRound + " " + this.isClick + " " + this.boxNum + " " + isBox;
    }



    public boolean isBox() {
        return isBox;
    }

    public void setBox(boolean box) {
        isBox = box;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }

    public Square(BoardLocation location) {
        this.location = location;
        isOpened = false;
        isFlag = false;
        hasLandMine = false;
        numberOfLandMine = 0;
        isClick = true;
    }

    public byte getNumberOfLandMineRound() {
        return numberOfLandMineRound;
    }

    public void setNumberOfLandMineRound(byte numberOfLandMineRound) {
        this.numberOfLandMineRound = numberOfLandMineRound;
    }

    public BoardLocation getLocation() {
        return location;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean hasLandMine() {
        return hasLandMine;
    }

    public byte getNumberOfLandMine() {
        return numberOfLandMine;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public void setHasLandMine(boolean hasLandMine) {
        this.hasLandMine = hasLandMine;
    }


    public void setNumberOfLandMine(byte numberOfLandMine) {
        this.numberOfLandMine = numberOfLandMine;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getNum() {
        if (!isOpened)
            return ItemUtil.maskNum;
        else return getNumberOfLandMine();
        // TODO: You should implement the method to give the number of the item store in the grid
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public boolean isAiMarked() {
        return isAiMarked;
    }

    public void setAiMarked(boolean aiMarked) {
        isAiMarked = aiMarked;
    }

    /*
    Each grid has five states: the first two include a grid that is not open or is marked;
    The last three are lattice is opened, if there are no mines around, do not show;
    The number of mines, if any;
    If it is a mine, draw a mine
     */
}
