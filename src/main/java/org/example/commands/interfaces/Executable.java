package org.example.commands.interfaces;

import org.example.commands.exceptions.NumberOfArguments;
import org.example.commands.model.Response;

public interface Executable {
    Response execute(String[] parameters) throws NumberOfArguments;
    boolean checkParameters(String[] parameters);
}
