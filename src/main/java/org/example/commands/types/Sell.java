package org.example.commands.types;

import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.interfaces.MonetaryTransaction;
import org.example.commands.model.Command;
import org.example.commands.model.Response;
import org.example.model.Currency;
import org.example.survice.ExchangeService;
import org.example.survice.exceptions.*;

public class Sell extends Command implements MonetaryTransaction {
    ExchangeService exchangeService;
    public Sell(ExchangeService exchangeService) {
        super(
                "sell",
                "<amount_to_sell> <currency_to_sell> <currency_to_buy>",
                "Продажа валюты"
        );
        this.exchangeService = exchangeService;
    }

    @Override
    public Response execute(String[] parameters) throws NumberOfArguments {
        super.execute(parameters);
        try {
            exchangeService.makeSellTransaction(
                    new Currency(parameters[1]),
                    new Currency(parameters[2]),
                    Double.parseDouble(parameters[0]));
        } catch (UnavailableCurrencyPair e) {
            return new Response(false, "Покупка не состоялась",
                    "Терминал не осуществляет обмен данных валют или валюты введены неверно");
        } catch (NoSuchCurrencyUser e) {
            return new Response(false, "Покупка не состоялась",
                    "Вы не храните средства в этой валюте или валюта введена неверно");
        }catch (NoSuchCurrencyTerminal e) {
            return new Response(false, "Покупка не состоялась",
                    "Терминал не хранит средства в этой валюте или валюта введена неверно");
        } catch (NoMoneyTerminal e) {
            return new Response(false, "Покупка не состоялась",
                    "У терминала недостаточно средств в выбранной валюте для совершения обмена");
        } catch (NoMoneyUser e) {
            return new Response(false, "Покупка не состоялась",
                    "У Вас недостаточно средств в выбранной валюте для совершения обмена");
        } catch (NumberFormatException e){
            return new Response(false, "Покупка не состоялась",
                    "Сумма обмена введена неверно");
        }
        return new Response(true, "Обмен успешно совершён","");
    }
    @Override
    public boolean checkParameters(String[] parameters) {
        return parameters.length == 3;
    }

    @Override
    public String makeKeyPhrase(String[] parameters) {
        return "Продажа " + parameters[0] + " " + parameters[1] + " в обмен на " + parameters[2];
    }
}
