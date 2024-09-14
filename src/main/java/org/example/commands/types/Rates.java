package org.example.commands.types;

import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.model.Command;
import org.example.commands.model.Response;
import org.example.model.CurrencyPair;
import org.example.survice.entity.Terminal;

import java.text.DecimalFormat;
import java.util.List;

public class Rates extends Command {
    Terminal terminal;
    public Rates(Terminal terminal) {
        super(
                "rates",
                "",
                "Просмотр курсов валют"
        );
        this.terminal = terminal;
    }


    @Override
    public Response execute(String[] parameters) throws NumberOfArguments {
        super.execute(parameters);
        List<CurrencyPair> pairs = terminal.getAvailableCurrencyPairs();
        if (pairs.isEmpty()) {
            return new Response(false, "Список не выведен", "У терминала нет доступных валютных пар для обмена");
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.######"); // Формат для отображения до 6 знаков после запятой
        StringBuilder stringBuilder = new StringBuilder("Список доступных валютных пар:\n");

        for (CurrencyPair pair : pairs) {
            String pairString = pair.toString();
            String[] parts = pairString.split(":");
            String currencyPair = parts[0].trim();
            double rate = Double.parseDouble(parts[1].trim());
            String formattedRate = decimalFormat.format(rate);
            stringBuilder.append(String.format("%-10s: %s\n", currencyPair, formattedRate));
        }

        return new Response(true, "Список успешно выведен", stringBuilder.toString());
    }



    @Override
    public boolean checkParameters(String[] parameters) {
        return parameters.length == 0;
    }
}
