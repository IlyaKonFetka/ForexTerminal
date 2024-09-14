package org.example.commands.types;

import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.model.Command;
import org.example.commands.model.Response;
import org.example.survice.entity.User;

import java.util.List;

public class History extends Command {
    private User user;
    public History(User user) {
        super(
                "history",
                "",
                "Просмотр истории транзакций"
        );
        this.user = user;
    }

    @Override
    public Response execute(String[] parameters) throws NumberOfArguments {
        super.execute(parameters);
        List<String>transactions = user.getTransactionHistory(8);
        if (transactions.isEmpty()){
            return new Response(false, "Список не выведен", "Операции ещё не производились");
        }
        StringBuilder stringBuilder = new StringBuilder("Список последних операций:\n");
        for (String str: transactions){
            stringBuilder.append(str).append("\n");
        }
        return new Response(true, "Список успешно выведен", stringBuilder.toString());
    }

    @Override
    public boolean checkParameters(String[] parameters) {
        return parameters.length == 0;
    }
}

