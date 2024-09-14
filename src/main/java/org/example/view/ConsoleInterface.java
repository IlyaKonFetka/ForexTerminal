package org.example.view;

public interface ConsoleInterface {
    void prompt();

    void print(Object obj);
    void println(Object obj);

    String read();
    String readln();
    boolean isCanRead();
    boolean isCanReadln();

    void printSuccessful(Object o);
    void printWarning(Object obj);
    void printError(Object o);
}
