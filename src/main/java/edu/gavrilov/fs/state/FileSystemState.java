package edu.gavrilov.fs.state;

import edu.gavrilov.fs.exception.FileSystemException;
import edu.gavrilov.fs.model.File;
import edu.gavrilov.fs.model.FileSystemNode;
import edu.gavrilov.fs.model.Folder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSystemState {
    public static final Folder ROOT = new Folder("", null);

    // юзер начинает всегда с рута
    private Folder currentFolder = ROOT;

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(Folder folder) {
        this.currentFolder = folder;
    }

    public List<FileSystemNode> buildPath(String path) {
        return buildPath(path, true);
    }

    public List<FileSystemNode> buildPath(String path, boolean includeLast) {
        if (path.equals("/")) {
            return Collections.singletonList(FileSystemState.ROOT);
        }

        boolean isAbsolutePath = path.startsWith("/");
        String[] pathParts = (isAbsolutePath ? path.replaceFirst("/", "") : path).split("/");
        Folder currentFolder = isAbsolutePath ? FileSystemState.ROOT : getCurrentFolder();

        List<FileSystemNode> fileSystemPath = new ArrayList<>();
        fileSystemPath.add(currentFolder);
        for (int i = 0; i < pathParts.length; i++) {
            if (!includeLast && i == pathParts.length - 1) {
                continue;
            }

            String pathPart = pathParts[i];
            FileSystemNode nextNode = pathPart.equals("..")
                    ? currentFolder.getParent()
                    : currentFolder.getChildren().get(pathPart);

            if (nextNode instanceof Folder folder) {
                currentFolder = folder;
                fileSystemPath.add(nextNode);
            } else if (nextNode instanceof File file && i != pathPart.length() - 1) {
                throw new FileSystemException("Invalid path: " + file + " is not a folder");
            } else if (nextNode == null) {
                throw new FileSystemException("Path does not exist");
            }

        }
        return fileSystemPath;
    }
}
