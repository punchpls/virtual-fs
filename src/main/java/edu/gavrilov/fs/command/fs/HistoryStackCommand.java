package edu.gavrilov.fs.command.fs;

import edu.gavrilov.fs.command.Command;
import edu.gavrilov.fs.exception.FileSystemException;
import edu.gavrilov.fs.model.FileSystemNode;
import edu.gavrilov.fs.model.Folder;
import edu.gavrilov.fs.state.FileSystemState;

import java.util.List;
import java.util.Stack;

public class HistoryStackCommand implements FileSystemCommand {

    private final FileSystemState fileSystemState;
    private static Stack <String> commands = new Stack<>();


    public HistoryStackCommand(FileSystemState fileSystemState) {
        this.fileSystemState = fileSystemState;
    }

    public static String getCommandFromStack(){
            return commands.pop();
    }

    public static void addCommandToStack ( String input ){
        try{
            commands.push(input);
        } catch (FileSystemException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void execute(String... args) {
        try{
            for (int i = 0; i < Integer.parseInt(args[0]); i++){
                System.out.println(this.getCommandFromStack());
            }
        } catch (FileSystemException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public String getName() {
        return "history";
    }
}
