package com.app.demo.customview.transaction;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bet implements Serializable {

    private int betID;
    private String betDateTime;
    private String betTitle;
    private String betStatus;
    private BetInformation betInformation;
    private StakeAndPayout stakeAndPayout;
    private List<Event> events = null;
    private String betSubTitle;
    private boolean isCashoutAvailable;

    public Bet() {
        events = new ArrayList<>();
    }

    public int getBetID() {
        return betID;
    }

    public String getBetDateTime() {
        return betDateTime;
    }

    public void setBetDateTime(String betDateTime) {
        this.betDateTime = betDateTime;
    }

    public String getBetTitle() {
        return betTitle;
    }

    public void setBetTitle(String betTitle) {
        this.betTitle = betTitle;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }

    public BetInformation getBetInformation() {
        return betInformation;
    }

    public void setBetInformation(BetInformation betInformation) {
        this.betInformation = betInformation;
    }

    public StakeAndPayout getStakeAndPayout() {
        return stakeAndPayout;
    }

    public void setStakeAndPayout(StakeAndPayout stakeAndPayout) {
        this.stakeAndPayout = stakeAndPayout;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getBetSubTitle() {
        return betSubTitle;
    }

    public void setBetSubTitle(String betSubTitle) {
        this.betSubTitle = betSubTitle;
    }

    public boolean isCashoutAvailable() {
        return isCashoutAvailable;
    }


    public boolean hasCashedOut() {
        return betStatus != null && betStatus.equals("Cashed Out");
    }

    public boolean isBetSettled() {
        return betStatus != null && betStatus.equals("Settled");
    }

    public boolean isValid() {
        if (betStatus != null && betSubTitle != null && betTitle != null && betDateTime != null &&
                events != null && betInformation != null && stakeAndPayout != null) {
            for(int i = 0; i < events.size(); i++) {
                if (!events.get(i).isValid()) {
                    return false;
                }
            }

            if (!betInformation.isValid()) {
                return false;
            }

            if (!stakeAndPayout.isValid()) {
                return false;
            }

            return true;
        }

        return false;
    }

    public static Bet newBet(int numLegs, int finishLegs) {
        Bet bet = new Bet();
        for(int i = 0; i < numLegs; i ++) {
            Event event = new Event();
            event.setBetStatus(i < finishLegs ? "WON" : "PENDING");

            if (i == 4 || i == 6) {
                event.setBetStatus("LOST");
            }

            bet.getEvents().add(event);
        }
        return bet;
    }
}

