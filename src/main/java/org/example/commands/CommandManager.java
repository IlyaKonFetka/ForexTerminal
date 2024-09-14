package org.example.commands;

import org.example.commands.interfaces.Executable;
import org.example.commands.model.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private Map<String, Command> commandRegistry;
    public CommandManager() {
        this.commandRegistry = new HashMap<>();
    }

    public void registerCommand(Command... commands) {
        for (Command command : commands) {
            if (commandRegistry.containsKey(command.getName())) {
                throw new IllegalArgumentException("Команда с именем " + command.getName() + " уже зарегистрирована.");
            }
            commandRegistry.put(command.getName(), command);
        }
    }


    public Executable getCommand(String commandName, String[] userInput) {
        return commandRegistry.get(commandName);
    }

    public Map<String, Command> getCommandRegistry() {
        return commandRegistry;
    }

}
