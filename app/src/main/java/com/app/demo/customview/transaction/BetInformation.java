package com.app.demo.customview.transaction;


import java.io.Serializable;

public class BetInformation implements Serializable {

    private String betType;
    private String betSelection;
    private int numberOfLines;

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public String getBetSelection() {
        return betSelection;
    }

    public void setBetSelection(String betSelection) {
        this.betSelection = betSelection;
    }

    public int getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    public boolean isValid() {
        return betType != null && betSelection != null;
    }
}

