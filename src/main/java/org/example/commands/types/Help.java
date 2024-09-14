package org.example.commands.types;

import org.example.commands.CommandManager;
import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.model.Command;
import org.example.commands.model.Response;

import java.util.Map;

public class Help extends Command {
    CommandManager commandManager;
    public Help(CommandManager commandManager) {
        super(
                "help",
                "",
                "Список доступных команд"
        );
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(String[] parameters) throws NumberOfArguments {
        super.execute(parameters);
        Map<String, Command> commandMap = commandManager.getCommandRegistry();
        if (commandMap.isEmpty()) {
            return new Response(false, "Список не выведен", "Доступных команд не обнаружено");
        }
        StringBuilder stringBuilder = new StringBuilder("Список доступных команд:\n");
        for (Map.Entry<String, Command> entry : commandMap.entrySet()) {
            String commandName = entry.getValue().getName();
            String commandDescription = entry.getValue().getDescription();
            stringBuilder.append(String.format("%-15s\t%s\n", commandName, commandDescription));
        }
        return new Response(true, "Список команд успешно выведен", stringBuilder.toString());
    }

    @Override
    public boolean checkParameters(String[] parameters) {
        return parameters.length == 0;
    }
}
