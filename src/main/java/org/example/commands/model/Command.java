package org.example.commands.model;

import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.interfaces.Describable;
import org.example.commands.interfaces.Executable;

public abstract class Command implements Describable, Executable {

    private final String name;
    private final String parameters;
    private final String description;

    public Command(String name, String parameters, String description) {
        this.name = name;
        this.parameters = parameters;
        this.description = description;
    }

    @Override
    public Response execute(String[] parameters) throws NumberOfArguments {
        if (!checkParameters(parameters)) throw new NumberOfArguments();
        return null;
    }

    @Override public String getName() {return name;}
    @Override public String getParameters() {return parameters;}
    @Override public String getDescription() {return description;}

}
