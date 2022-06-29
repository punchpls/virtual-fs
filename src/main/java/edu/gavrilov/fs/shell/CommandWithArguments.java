package edu.gavrilov.fs.shell;

import java.util.Arrays;

public class CommandWithArguments {
    private final String command;
    private final String[] args;

    public CommandWithArguments(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

    public static CommandWithArguments build(String input) {
        String[] inputParts = input.split(" ");
        String command = inputParts[0];
        String[] args = Arrays.copyOfRange(inputParts, 1, inputParts.length);
        return new CommandWithArguments(command, args);
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return command + " " + String.join(" ", getArgs());
    }
}
