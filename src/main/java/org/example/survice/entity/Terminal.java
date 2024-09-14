package org.example.survice.entity;

import org.example.model.Currency;
import org.example.model.CurrencyPair;
import org.example.survice.exceptions.NoSuchCurrencyException;
import org.example.survice.exceptions.UnavailableCurrencyPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Terminal extends EntityHaveBalance {

    private List<CurrencyPair> availableCurrencyPairs;

    public Terminal() {
        super();
        this.availableCurrencyPairs = new ArrayList<>();
    }

    public List<CurrencyPair> getAvailableCurrencyPairs() {
        return availableCurrencyPairs;
    }

    public void registerCurrencyPair(CurrencyPair... pairs) throws NoSuchCurrencyException {
        for (CurrencyPair pair : pairs) {
            if (!balance.containsKey(pair.getBaseCurrency()) || !balance.containsKey(pair.getQuoteCurrency())) {
                throw new NoSuchCurrencyException();
            }
            availableCurrencyPairs.add(pair);
        }
    }

    public CurrencyPair getCurrencyPair(Currency currency1, Currency currency2) throws UnavailableCurrencyPair {
        for (CurrencyPair pair : availableCurrencyPairs) {
            if (pair.matchesPair(currency1, currency2)) {
                return pair;
            }
        }
        throw new UnavailableCurrencyPair();
    }
    public void updateCurrencyRates() {
        Random random = new Random();
        for (CurrencyPair pair : availableCurrencyPairs) {
            float percentageChange = -5 + random.nextFloat() * 10; // Генерация числа от -5 до +5
            float coefficient = 1 + (percentageChange / 100); // Преобразование в коэффициент
            pair.updateExchangeRate(coefficient); // Обновление курса
        }
    }
}


