package org.example.survice.entity;

import org.example.model.Currency;
import org.example.survice.exceptions.NoMoneyException;
import org.example.survice.exceptions.NoSuchCurrencyException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class EntityHaveBalance {
    protected Map<Currency, Double> balance;

    public EntityHaveBalance() {
        this.balance = new LinkedHashMap<>();
    }

    public void registerCurrency(Currency... currencies) {
        for (Currency currency : currencies) {
            if (!balance.containsKey(currency)) {
                balance.put(currency, 0.0);
            }
        }
    }


    public Set<Currency> getAvailableCurrencies(){
        return balance.keySet();
    }

    public Map<Currency, Double> getBalance() {
        return balance;
    }

    public void deposit(Currency currency, double amount) throws NoSuchCurrencyException {
        if (!balance.containsKey(currency)) {
            throw new NoSuchCurrencyException();
        }
        balance.put(currency, balance.getOrDefault(currency, 0.0) + amount);
    }

    public void withdraw(Currency currency, double amount) throws NoSuchCurrencyException, NoMoneyException {
        if (!balance.containsKey(currency)) {
            throw new NoSuchCurrencyException();
        }
        if (balance.get(currency) >= amount) {
            balance.put(currency, balance.get(currency) - amount);
        } else {
            throw new NoMoneyException();
        }
    }
}
