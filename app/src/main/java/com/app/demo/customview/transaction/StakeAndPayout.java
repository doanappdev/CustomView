package com.app.demo.customview.transaction;


import java.io.Serializable;

public class StakeAndPayout implements Serializable {

    private double totalStake;
    private double totalReturn;
    private double price;

    public double getTotalStake() {
        return totalStake;
    }

    public void setTotalStake(double totalStake) {
        this.totalStake = totalStake;
    }

    public double getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(double totalReturn) {
        this.totalReturn = totalReturn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isValid() {
        return true;
    }
}

