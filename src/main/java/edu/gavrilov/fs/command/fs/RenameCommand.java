package edu.gavrilov.fs.command.fs;

import edu.gavrilov.fs.exception.FileSystemException;
import edu.gavrilov.fs.model.File;
import edu.gavrilov.fs.model.FileSystemNode;
import edu.gavrilov.fs.model.Folder;
import edu.gavrilov.fs.state.FileSystemState;

import java.util.List;

public class RenameCommand implements FileSystemCommand {

    private final FileSystemState fileSystemState;

    public RenameCommand(FileSystemState fileSystemState) {
        this.fileSystemState = fileSystemState;
    }

    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            System.err.println("Rename command has to be invoked with exactly 2 arguments");
        }

        String originalName = args[0];
        String newName = args[1];

        try {
            if (newName.contains("/")) {
                throw new FileSystemException("New name must not contain /");
            }

            List<FileSystemNode> originalPath = fileSystemState.buildPath(originalName);
            FileSystemNode renamedNode = originalPath.get(originalPath.size() - 1);
            Folder renamedNodeParent = (Folder) renamedNode.getParent();

            if (renamedNode instanceof Folder folderToRename) {
                Folder renamedFolder = new Folder(newName, folderToRename.getParent());
                for (FileSystemNode child : folderToRename.getChildren().values()) {
                    renamedFolder.addChild(child);
                }
                renamedNodeParent.removeChild(originalName);
                renamedNodeParent.addChild(renamedFolder);
            } else if (renamedNode instanceof File fileToRename) {
                File renamedFile = new File(newName, fileToRename.getParent());
                renamedNodeParent.removeChild(originalName);
                renamedNodeParent.addChild(renamedFile);
            }
        } catch (FileSystemException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public String getName() {
        return "rename";
    }
}
