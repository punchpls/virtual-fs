package edu.gavrilov.fs.command.fs;

import edu.gavrilov.fs.exception.FileSystemException;
import edu.gavrilov.fs.model.File;
import edu.gavrilov.fs.model.FileSystemNode;
import edu.gavrilov.fs.model.Folder;
import edu.gavrilov.fs.state.FileSystemState;

import java.util.List;
import java.util.Map;

import static edu.gavrilov.fs.model.FormattingConstants.*;

public class ListCommand implements FileSystemCommand {

    private final FileSystemState fileSystemState;

    public ListCommand(FileSystemState fileSystemState) {
        this.fileSystemState = fileSystemState;
    }

    @Override
    public void execute(String... args) {
        if (args.length > 1) {
            System.err.println("List command has to be invoked with 0 or 1 argument");
        }

        try {
            Map<String, FileSystemNode> children;
            if (args.length == 0) {
                children = fileSystemState.getCurrentFolder().getChildren();
            } else {
                String inputPath = args[0];
                List<FileSystemNode> path = fileSystemState.buildPath(inputPath);
                FileSystemNode nodeToList = path.get(path.size() - 1);
                if (nodeToList instanceof Folder listedFolder) {
                    children = listedFolder.getChildren();
                } else {
                    throw new FileSystemException(nodeToList + " is not a folder");
                }
            }

            System.out.println();
            for (FileSystemNode child : children.values()) {
                if (child instanceof Folder) {
                    System.out.println(ANSI_BLUE + child.getName() + ANSI_RESET);
                }
            }
            for (FileSystemNode child : children.values()) {
                if (child instanceof File) {
                    System.out.println(ANSI_GREEN + child.getName() + ANSI_RESET);
                }
            }
            System.out.println("total: " + children.size());

        } catch (FileSystemException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public String getName() {
        return "ls";
    }
}
