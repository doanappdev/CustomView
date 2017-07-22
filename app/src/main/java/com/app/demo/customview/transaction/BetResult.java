package com.app.demo.customview.transaction;


import java.io.Serializable;

public class BetResult implements Serializable {

    private String dividendTypeCode;
    private String marketTypeCode;
    private double price;

    public String getDividendTypeCode() {
        return dividendTypeCode;
    }

    public void setDividendTypeCode(String dividendTypeCode) {
        this.dividendTypeCode = dividendTypeCode;
    }

    public String getMarketTypeCode() {
        return marketTypeCode;
    }

    public void setMarketTypeCode(String marketTypeCode) {
        this.marketTypeCode = marketTypeCode;
    }

    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }

}

