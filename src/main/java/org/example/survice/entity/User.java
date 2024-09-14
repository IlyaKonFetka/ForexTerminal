package org.example.survice.entity;

import java.util.ArrayList;
import java.util.List;

public class User extends EntityHaveBalance {
    private List<String> transactionHistory;

    public User() {
        super();
        this.transactionHistory = new ArrayList<>();
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public List<String> getTransactionHistory(int limit) {
        int size = transactionHistory.size();
        int startIndex = Math.max(0, size - limit);
        return new ArrayList<>(transactionHistory.subList(startIndex, size));
    }

}
