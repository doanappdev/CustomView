package com.app.demo.customview.transaction;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Transaction extends BaseViewModel implements Serializable {

    private long transactionID;
    private Date transactionDate;
    private int transactionAmount;
    private String transactionType;
    private int betID;
    private Bet bet;

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public int getBetID() {
        return betID;
    }

    public int getBetId() {
        return bet.getBetID();
    }

    public boolean canCashOut() {
        //return bet.isCashoutAvailable() && ServiceManager.getCashOutService().isCashOutEnabled();
        return true;
    }

    public boolean hasCashedOut() {
        return bet.hasCashedOut();
    }

    public boolean isBetSettled() {
        return bet.isBetSettled();
    }

    public String getTitle() {
        return bet.getBetTitle();
    }

    public String getSubTitle() {
        return bet.getBetSubTitle();
    }

    public int getReceiptId() {
        return bet.getBetID();
    }

    public boolean isBetPlacementType() {
        return transactionType.equals("Bet Placement");
    }

    public boolean isBetPayoutType() {
        return transactionType.equals("Bet Payout");
    }

    public boolean isBonusBet() {
        return false;
    }

    public boolean isBoostBet() {
        return false;
    }

    public boolean isSingleBet() {
        return bet.getBetInformation().getBetType().equals("Single");
    }

    public boolean isMultipleBet() {
        return bet.getBetInformation().getBetType().equals("Multiple");
    }

    public boolean isEachWayBet() {
        return bet.getEvents().size() == 2;
    }

    public boolean isBetTransaction() {
        return transactionType.equals("Bet Placement");
    }

    public boolean isSportsBet() {
        return false;
    }

    public boolean isLottoType() {
        return false;
    }

    public boolean isDeposit() {
        return false;
    }

    public double getPrice(int index) {
        if (bet.getEvents() != null && index < bet.getEvents().size())
            return bet.getEvents().get(index).getPrice();
        else
            return 0;
    }

    public String getEventTitle(int index) {
        if (bet.getEvents() == null || index < bet.getEvents().size())
            return bet.getEvents().get(index).getEventTitle();
        else
            return "";
    }

    public String getEventSubTitle(int index) {
        if (bet.getEvents() == null || index < bet.getEvents().size())
            return bet.getEvents().get(index).getEventSubTitle();
        else
            return "";
    }

    public List<Event> getEvents() {
        return bet.getEvents();
    }

    public boolean isFreeBet() {
        return false;
    }

    public String getBetSelection() {
        return bet.getBetInformation().getBetSelection();
    }

    public String getBetType() {
        return bet.getBetInformation().getBetType();
    }

    public double getTotalLines() {
        return bet.getBetInformation().getNumberOfLines();
    }

    public double getTotalPayout() {
        return bet.getStakeAndPayout().getTotalReturn();
    }

    public double getPayoutPrice() {
        return bet.getStakeAndPayout().getPrice();
    }

    public double getTotalStake() {
        return bet.getStakeAndPayout().getTotalStake();
    }

    @Override
    public int getUniqueId() {
        return 0;
    }

    @Override
    public boolean isValid() {
        if (transactionID != 0 && transactionType != null && betID != 0) {
            if (bet != null) {
                return bet.isValid();
            }
            return true;
        }
        return false;
    }
}


