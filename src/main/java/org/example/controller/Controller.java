package org.example.controller;

import org.example.commands.CommandManager;
import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.interfaces.MonetaryTransaction;
import org.example.commands.model.Command;
import org.example.commands.model.Response;
import org.example.survice.entity.Terminal;
import org.example.survice.entity.User;
import org.example.view.ConsoleInterface;

public class Controller {
    private ConsoleInterface console;
    private CommandManager commandManager;
    private User user;
    Terminal terminal;

    public Controller(ConsoleInterface console, CommandManager commandManager, User user, Terminal terminal) {
        this.console = console;
        this.commandManager = commandManager;
        this.user = user;
        this.terminal = terminal;
    }

    public void greeting(){
        console.println("""
                        Добро пожаловать в ForexTerminal!
                        Здесь вы управляете своими финансами.
                        Введите help, чтобы получить список доступных команд."""
                );
    }
    public void start(){
        String commandName;
        String[] parameters;
        Response response;
        boolean alive = true;
        greeting();
        while (alive){
            console.prompt();

            commandName = console.read().trim();
            String input = console.readln().trim();
            parameters = input.isEmpty() ? new String[0] : input.split("\\s+");

            if (commandName.isEmpty())continue;
            if (commandName.equals("exit")) alive = false;
            response = prepareCommand(commandName, parameters);
            if (response == null)continue;
            if(response.status()){
                console.println(response.textBody());
                console.printSuccessful(response.textStatus());
            }else {
                console.println(response.textBody());
                console.printError(response.textStatus());
            }
        }
    }

    public Response prepareCommand(String commandName, String[] parameters){
        Command command = (Command) commandManager.getCommand(commandName,parameters);
        Response response;
        if (command == null) {
            console.printWarning("Команда " + commandName + " не найдена. Введи help, чтобы получить список команд");
            return null;
        }
        try {
            response = command.execute(parameters);
        } catch (NumberOfArguments e) {
            console.printError("Неверное количество аргументов: " + parameters.length);
            console.println("Введите команду в формате:");
            console.println(command.getName() + " " + command.getParameters());
            return null;
        }
        if (command instanceof MonetaryTransaction){
            if (response.status()){
                user.addTransaction(((MonetaryTransaction) command).makeKeyPhrase(parameters));
                terminal.updateCurrencyRates();
            }
        }
        return response;
    }
}
//обрабатывает ввод пользователя и передает его в виде request сервису