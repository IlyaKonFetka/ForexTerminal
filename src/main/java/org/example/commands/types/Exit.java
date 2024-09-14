package org.example.commands.types;

import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.model.Command;
import org.example.commands.model.Response;
import org.example.view.ConsoleInterface;

public class Exit extends Command {
    ConsoleInterface console;
    public Exit(ConsoleInterface console) {
        super(
                "exit",
                "",
                "Завершение программы"
        );
        this.console = console;
    }

    @Override
    public Response execute(String[] parameters) throws NumberOfArguments {
        super.execute(parameters);
        console.printSuccessful("Завершение работы...");
        System.exit(777);
        return null;
    }

    @Override
    public boolean checkParameters(String[] parameters) {
        return parameters.length == 0;
    }
}

