package com.app.demo.customview.transaction;


import java.util.Date;

public class Event {

    private int eventTypeID;
    private String eventTitle;
    private String eventSubTitle;
    private Date advertisedStartTime;
    private BetResult betResult;
    private String betStatus;

    public int getEventTypeID() {
        return eventTypeID;
    }

    public void setEventTypeID(int eventTypeID) {
        this.eventTypeID = eventTypeID;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventSubTitle() {
        return eventSubTitle;
    }

    public void setEventSubTitle(String eventSubTitle) {
        this.eventSubTitle = eventSubTitle;
    }

    public Date getAdvertisedStartTime() {
        return advertisedStartTime;
    }

    public void setAdvertisedStartTime(Date advertisedStartTime) {
        this.advertisedStartTime = advertisedStartTime;
    }

    public BetResult getBetResult() {
        return betResult;
    }

    public void setBetResult(BetResult betResult) {
        this.betResult = betResult;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }

    public boolean isSportEvent() {
        return eventTypeID >= 100;
    }

    public boolean betLost() {
        return betStatus.equals("LOST");
    }

    public boolean isValid() {
        return betStatus != null && betResult != null && advertisedStartTime != null && eventTypeID != 0;
    }


    public double getPrice() {
        return betResult.getPrice();
    }

    public String getDividendTypeCode() {
        return betResult.getDividendTypeCode();
    }

    public String getMarketTypeCode() {
        return betResult.getMarketTypeCode();
    }
}

