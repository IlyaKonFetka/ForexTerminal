package org.example.model;

import org.example.survice.exceptions.UnavailableCurrencyPair;

public class CurrencyPair {
    private Currency baseCurrency;
    private Currency quoteCurrency;
    private float exchangeRate;

    public CurrencyPair(Currency baseCurrency, Currency quoteCurrency, float exchangeRate) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return baseCurrency + "/" + quoteCurrency + ": " + exchangeRate;
    }

    public boolean matchesPair(Currency currency1, Currency currency2) {
        return (baseCurrency.equals(currency1) && quoteCurrency.equals(currency2)) ||
                (baseCurrency.equals(currency2) && quoteCurrency.equals(currency1));
    }

    public float getUniversalRate(Currency currencyFrom, Currency currencyTo) throws UnavailableCurrencyPair {
        if (currencyFrom.equals(baseCurrency) && currencyTo.equals(quoteCurrency)) {
            return exchangeRate;
        } else if (currencyFrom.equals(quoteCurrency) && currencyTo.equals(baseCurrency)) {
            return 1 / exchangeRate;
        }
        throw new UnavailableCurrencyPair();
    }

    public void updateExchangeRate(float coefficient) {
        this.exchangeRate *= coefficient;
    }

    // Геттеры для полей, если необходимо
    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getQuoteCurrency() {
        return quoteCurrency;
    }

    public float getExchangeRate() {
        return exchangeRate;
    }
}
