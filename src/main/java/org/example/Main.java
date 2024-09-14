package org.example;

import org.example.commands.CommandManager;
import org.example.commands.model.Command;
import org.example.commands.types.*;
import org.example.controller.Controller;
import org.example.model.Currency;
import org.example.model.CurrencyPair;
import org.example.survice.ExchangeService;
import org.example.survice.entity.Terminal;
import org.example.survice.entity.User;
import org.example.survice.exceptions.NoSuchCurrencyException;
import org.example.view.SimpleConsole;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SimpleConsole console = new SimpleConsole(new Scanner(System.in));

        final Currency USD =  new Currency("USD");
        final Currency RUB =  new Currency("RUB");
        final Currency EUR =  new Currency("EUR");
        final Currency USDT = new Currency("USDT");
        final Currency BTC =  new Currency("BTC");

        User user = new User();
        user.registerCurrency(USD, RUB, EUR, USDT, BTC);
        try {
            user.deposit(RUB, 1_000_000);
        } catch (NoSuchCurrencyException ignored) {}

        Terminal terminal = new Terminal();
        terminal.registerCurrency(USD, RUB, EUR, USDT, BTC);
        try {
            terminal.deposit(RUB, 10_000);
            terminal.deposit(USD, 1_000);
            terminal.deposit(EUR, 1_000);
            terminal.deposit(USDT, 1_000);
            terminal.deposit(BTC, 1.5);
        } catch (NoSuchCurrencyException ignored) {}

        CurrencyPair RUB_USD = new CurrencyPair(RUB, USD, 0.010997f);
        CurrencyPair RUB_EUR = new CurrencyPair(RUB, EUR, 0.009921f);
        CurrencyPair USD_EUR = new CurrencyPair(USD, EUR, 0.90275f);
        CurrencyPair USD_USDT = new CurrencyPair(USD, USDT, 1f);
        CurrencyPair USD_BTC = new CurrencyPair(USD, BTC, 0.000017f);
        try {
            terminal.registerCurrencyPair(RUB_USD, RUB_EUR, USD_EUR, USD_USDT, USD_BTC);
        } catch (NoSuchCurrencyException ignored) {}

        ExchangeService exchangeService = new ExchangeService(terminal, user);

        CommandManager commandManager = new CommandManager();
        Balance balance = new Balance(user);
        Buy buy = new Buy(exchangeService);
        Deposit deposit = new Deposit(user);
        Exit exit = new Exit(console);
        Help help = new Help(commandManager);
        History history = new History(user);
        Rates rates = new Rates(terminal);
        Sell sell = new Sell(exchangeService);
        Withdraw withdraw = new Withdraw(user);
        commandManager.registerCommand(balance, buy, deposit, exit, help, history, rates, sell, withdraw);

        Controller controller = new Controller(console, commandManager, user, terminal);
        controller.start();
    }
}