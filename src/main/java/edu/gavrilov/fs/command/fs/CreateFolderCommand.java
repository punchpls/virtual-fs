package edu.gavrilov.fs.command.fs;

import edu.gavrilov.fs.exception.FileSystemException;
import edu.gavrilov.fs.model.FileSystemNode;
import edu.gavrilov.fs.model.Folder;
import edu.gavrilov.fs.state.FileSystemState;

import java.util.List;

public class CreateFolderCommand implements FileSystemCommand {

    private final FileSystemState fileSystemState;

    public CreateFolderCommand(FileSystemState fileSystemState) {
        this.fileSystemState = fileSystemState;
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.err.println("Create folder command has to be invoked with exactly 1 argument");
        }

        String inputPath = args[0];
        try {
            String folderName = inputPath.contains("/") ? inputPath.substring(inputPath.lastIndexOf("/") + 1) : inputPath;
            List<FileSystemNode> path = fileSystemState.buildPath(inputPath, false);
            if (path.get(path.size() - 1) instanceof Folder parent) {
                Folder folder = new Folder(folderName, parent);
                if (parent.getChildren().get(folderName) == null) {
                    parent.addChild(folder);
                } else {
                    throw new FileSystemException("Folder " + folder + " already exists");
                }
            }
        } catch (FileSystemException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public String getName() {
        return "mkdir";
    }
}
