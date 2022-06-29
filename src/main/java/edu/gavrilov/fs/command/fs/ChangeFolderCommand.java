package edu.gavrilov.fs.command.fs;

import edu.gavrilov.fs.exception.FileSystemException;
import edu.gavrilov.fs.model.FileSystemNode;
import edu.gavrilov.fs.model.Folder;
import edu.gavrilov.fs.state.FileSystemState;

import java.util.List;

public class ChangeFolderCommand implements FileSystemCommand {
    private final FileSystemState fileSystemState;

    public ChangeFolderCommand(FileSystemState fileSystemState) {
        this.fileSystemState = fileSystemState;
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.err.println("Create folder command has to be invoked with exactly 1 element");
        }
        String inputPath = args[0];

        try {
            List<FileSystemNode> path = fileSystemState.buildPath(inputPath);
            FileSystemNode destination = path.get(path.size() - 1);
            if (destination instanceof Folder folder) {
                fileSystemState.setCurrentFolder(folder);
            } else {
                throw new FileSystemException("Invalid path: " + destination + " is not a folder");
            }
        } catch (FileSystemException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public String getName() {
        return "cd";
    }
}
