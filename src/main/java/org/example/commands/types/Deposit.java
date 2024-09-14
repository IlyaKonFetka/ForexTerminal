package org.example.commands.types;

import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.interfaces.MonetaryTransaction;
import org.example.commands.model.Command;
import org.example.commands.model.Response;
import org.example.model.Currency;
import org.example.survice.entity.User;
import org.example.survice.exceptions.NoSuchCurrencyException;

import java.util.Map;
import java.util.Set;

public class Deposit extends Command implements MonetaryTransaction {
    private User user;

    public Deposit(User user) {
        super(
                "deposit",
                "<amount> <currency>",
                "Пополнение баланса"
        );
        this.user = user;
    }

    @Override
    public Response execute(String[] parameters) throws NumberOfArguments {
        super.execute(parameters);
        try {
            user.deposit(new Currency(parameters[1]), Double.parseDouble(parameters[0]));
        } catch (NoSuchCurrencyException e) {
            return new Response(false, "Пополнения не произошло",
                    "У вас отсутствует счёт в данной валюте или валюта была введена неверно");
        } catch (NumberFormatException e){
            return new Response(false, "Пополнения не произошло",
                    "Неверный формат суммы средств");
        }
        return new Response(true, "Баланс успешно пополнен","");
    }

    @Override
    public boolean checkParameters(String[] parameters) {
        return parameters.length == 2;
    }

    @Override
    public String makeKeyPhrase(String[] parameters) {
        return "Пополнение баланса на " + parameters[0] + " " + parameters[1];
    }
}

