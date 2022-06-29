package edu.gavrilov.fs.command.fs;

import edu.gavrilov.fs.state.FileSystemState;

public class PrintWorkingDirectoryCommand implements FileSystemCommand {

    private final FileSystemState fileSystemState;

    public PrintWorkingDirectoryCommand(FileSystemState fileSystemState) {
        this.fileSystemState = fileSystemState;
    }

    @Override
    public void execute(String... args) {
        System.out.println(this.fileSystemState.getCurrentFolder());
    }

    @Override
    public String getName() {
        return "pwd";
    }
}
