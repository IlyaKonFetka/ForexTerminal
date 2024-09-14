package org.example.survice;

import org.example.model.Currency;
import org.example.model.CurrencyPair;
import org.example.survice.entity.Terminal;
import org.example.survice.entity.User;
import org.example.survice.exceptions.*;

public class ExchangeService {
    private final Terminal terminal;
    private final User user;

    public ExchangeService(Terminal terminal, User user) {
        this.terminal = terminal;
        this.user = user;
    }

    private void userToTerminal(Currency currency, double amount) throws NoMoneyException, NoSuchCurrencyTerminal, NoSuchCurrencyUser {
        try {
            user.withdraw(currency, amount);
        } catch (NoSuchCurrencyException e) {
            throw new NoSuchCurrencyUser();
        }
        try {
            terminal.deposit(currency, amount);
        } catch (NoSuchCurrencyException e) {
            throw new NoSuchCurrencyTerminal();
        }
    }

    private void terminalToUser(Currency currency, double amount) throws NoMoneyException, NoSuchCurrencyUser, NoSuchCurrencyTerminal {
        try {
            terminal.withdraw(currency, amount);
        } catch (NoSuchCurrencyException e) {
            throw new NoSuchCurrencyTerminal();
        }
        try {
            user.deposit(currency, amount);
        } catch (NoSuchCurrencyException e) {
            throw new NoSuchCurrencyUser();
        }
    }

    public void makeBuyTransaction(Currency currencyFrom, Currency currencyTo, double fromTerminal)
            throws UnavailableCurrencyPair, NoMoneyTerminal, NoMoneyUser, NoSuchCurrencyTerminal, NoSuchCurrencyUser {
        CurrencyPair currencyPair = terminal.getCurrencyPair(currencyFrom,currencyTo);
        float rate = currencyPair.getUniversalRate(currencyFrom,currencyTo);
        double fromUser = fromTerminal / rate;
        try {
            terminalToUser(currencyTo, fromTerminal);
        } catch (NoMoneyException e) {
            throw new NoMoneyTerminal();
        }
        try {
            userToTerminal(currencyFrom, fromUser);
        } catch (NoMoneyException e) {
            throw new NoMoneyUser();
        }
    }
    public void makeSellTransaction(Currency currencyFrom, Currency currencyTo, double fromUser)
            throws UnavailableCurrencyPair, NoMoneyTerminal, NoMoneyUser, NoSuchCurrencyUser, NoSuchCurrencyTerminal {
        CurrencyPair currencyPair = terminal.getCurrencyPair(currencyFrom,currencyTo);
        float rate = currencyPair.getUniversalRate(currencyFrom,currencyTo);
        double fromTerminal = fromUser * rate;
        try {
            terminalToUser(currencyTo, fromTerminal);
        } catch (NoMoneyException e) {
            throw new NoMoneyTerminal();
        }
        try {
            userToTerminal(currencyFrom, fromUser);
        } catch (NoMoneyException e) {
            throw new NoMoneyUser();
        }
    }

}
//Совершает "транзакции" между пользователем и терминалом.