package edu.gavrilov.fs.command;

public interface Command {
    void execute(String... args);

    String getName();
}
