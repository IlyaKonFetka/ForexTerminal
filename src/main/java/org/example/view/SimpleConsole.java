package org.example.view;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class SimpleConsole implements ConsoleInterface{

    private static final String P = ">: ";
    private final Scanner scanner;

    public SimpleConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void prompt() {
        System.out.print(P);
    }

    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public String read() throws NoSuchElementException, IllegalStateException {
        return scanner.next();
    }
    @Override
    public String readln() throws NoSuchElementException, IllegalStateException {
        return scanner.nextLine();
    }

    @Override
    public boolean isCanRead() throws IllegalStateException {
        return scanner.hasNext();
    }
    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return scanner.hasNextLine();
    }

    @Override
    public void printWarning(Object obj) {
        String brightYellowColorCode = "\u001B[33;1m";
        String resetColorCode = "\u001B[0m";
        System.out.println(brightYellowColorCode + obj + resetColorCode);
    }

    @Override
    public void printSuccessful(Object o) {
        String brightGreenColorCode = "\u001B[32;1m";
        String resetColorCode = "\u001B[0m";
        System.out.println(brightGreenColorCode + o + resetColorCode);
    }

    @Override
    public void printError(Object o) {
        String brightRedColorCode = "\u001B[31;1m";
        String resetColorCode = "\u001B[0m";
        System.out.println(brightRedColorCode + o + resetColorCode);
    }

}
