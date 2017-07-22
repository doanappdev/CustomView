package com.app.demo.customview.transaction;


import java.util.List;

public class TransactionList extends BaseViewModel implements Response {

    private List<Transaction> transactions = null;
    private int totalRecords;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    @Override
    public boolean isSuccessful() {
        return true;
    }

    @Override
    public int getUniqueId() {
        return 0;
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
