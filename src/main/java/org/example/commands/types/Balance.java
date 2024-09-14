package org.example.commands.types;

import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.model.Command;
import org.example.commands.model.Response;
import org.example.model.Currency;
import org.example.survice.entity.User;

import java.util.Map;

public class Balance extends Command {
    private User user;

    public Balance(User user) {
        super(
                "balance",
                "",
                "Просмотр баланса"
        );
        this.user = user;
    }


    @Override
    public Response execute(String[] parameters) throws NumberOfArguments {
        super.execute(parameters);
        Map<Currency, Double> balance = user.getBalance();
        StringBuilder stringBuilder = new StringBuilder("Ваш баланс:\n");

        if (balance.isEmpty()) {
            return new Response(false, "Баланс отсутствует","Похоже, что вы не храните средства ни в одной валюте");
        } else {
            for (Map.Entry<Currency, Double> entry : balance.entrySet()) {
                Currency currency = entry.getKey();
                Double amount = entry.getValue();
                stringBuilder.append(String.format("%-4s: %.2f\n", currency, amount));
            }
        }
        return new Response(true, "Баланс успешно выведен",stringBuilder.toString());
    }


    @Override
    public boolean checkParameters(String[] parameters) {
        return parameters.length == 0;
    }
}