package edu.gavrilov.fs.command.fs;

import edu.gavrilov.fs.exception.FileSystemException;
import edu.gavrilov.fs.model.File;
import edu.gavrilov.fs.model.FileSystemNode;
import edu.gavrilov.fs.model.Folder;
import edu.gavrilov.fs.state.FileSystemState;

import java.util.List;

public class CreateFileCommand implements FileSystemCommand {

    private final FileSystemState fileSystemState;

    public CreateFileCommand(FileSystemState fileSystemState) {
        this.fileSystemState = fileSystemState;
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.err.println("Create file command has to be invoked with exactly 1 argument");
        }

        String inputPath = args[0];
        try {
            String fileName = inputPath.contains("/") ? inputPath.substring(inputPath.lastIndexOf("/") + 1) : inputPath;
            List<FileSystemNode> path = fileSystemState.buildPath(inputPath, false);
            if (path.get(path.size() - 1) instanceof Folder folder) {
                File file = new File(fileName, folder);
                if (folder.getChildren().get(fileName) == null) {
                    folder.addChild(file);
                } else {
                    throw new FileSystemException("File " + file + " already exists");
                }
            }
        } catch (FileSystemException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public String getName() {
        return "touch";
    }
}
