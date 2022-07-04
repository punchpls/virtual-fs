package edu.gavrilov.fs.shell;

import edu.gavrilov.fs.command.Command;
import edu.gavrilov.fs.command.fs.HistoryStackCommand;
import edu.gavrilov.fs.state.FileSystemState;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandShell {

    private final Map<String, Command> supportedCommands = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private boolean stackIsRequired = false;

    public CommandShell(Command... supportedCommands) {
        for (Command command : supportedCommands) {
            registerCommand(command);
        }
    }

    public CommandShell(boolean stackIsRequired, Command... supportedCommands) {
        for (Command command : supportedCommands) {
            registerCommand(command);
        }
        this.stackIsRequired = stackIsRequired;
    }

    public void registerCommand(Command command) {
        this.supportedCommands.put(command.getName(), command);
    }

    public void init(FileSystemState fileSystemState) {
        while (true) {
            System.out.print(fileSystemState.getCurrentFolder());
            System.out.print(" > ");
            String input = scanner.nextLine();
            CommandWithArguments commandWithArguments = CommandWithArguments.build(input);
            Command command = supportedCommands.get(commandWithArguments.getCommand());
            String[] args = commandWithArguments.getArgs();

            if (command != null) {
                if (stackIsRequired && !command.getName().equals("history")){
                    HistoryStackCommand.addCommandToStack(input);
                }
                command.execute(args);
            } else {
                System.out.println("Command not recognized");
            }
        }
    }

}
