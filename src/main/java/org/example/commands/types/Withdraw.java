package org.example.commands.types;

import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.interfaces.MonetaryTransaction;
import org.example.commands.model.Command;
import org.example.commands.model.Response;
import org.example.model.Currency;
import org.example.survice.entity.User;
import org.example.survice.exceptions.NoMoneyException;
import org.example.survice.exceptions.NoSuchCurrencyException;

public class Withdraw extends Command implements MonetaryTransaction {
    User user;
    public Withdraw(User user) {
        super(
                "withdraw",
                "<amount> <currency>",
                "Снятие средств"
        );
        this.user = user;
    }

    @Override
    public Response execute(String[] parameters) throws NumberOfArguments {
        super.execute(parameters);
        try {
            user.withdraw(new Currency(parameters[1]), Double.parseDouble(parameters[0]));
        } catch (NoSuchCurrencyException e) {
            return new Response(false, "Вывод не состоялся",
                    "Вы не храните средства в этой валюте или валюта введена неверно");
        } catch (NoMoneyException e) {
            return new Response(false, "Вывод не состоялся",
                    "У Вас недостаточно средств в выбранной валюте для совершения вывода");
        } catch (NumberFormatException e){
            return new Response(false, "Покупка не состоялась",
                    "Сумма обмена введена неверно");
        }
        return new Response(true, "Вывод средств успешен","");
    }

    @Override
    public boolean checkParameters(String[] parameters) {
        return parameters.length == 2;
    }

    @Override
    public String makeKeyPhrase(String[] parameters) {
        return "Вывод " + parameters[0] + " " + parameters[1];
    }
}

