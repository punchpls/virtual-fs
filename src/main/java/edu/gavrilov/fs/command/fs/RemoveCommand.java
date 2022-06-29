package edu.gavrilov.fs.command.fs;

import edu.gavrilov.fs.command.Command;
import edu.gavrilov.fs.model.FileSystemNode;
import edu.gavrilov.fs.model.Folder;
import edu.gavrilov.fs.state.FileSystemState;

import java.util.ArrayList;
import java.util.List;

public class RemoveCommand implements Command {

    private final FileSystemState fileSystemState;

    public RemoveCommand(FileSystemState fileSystemState) {
        this.fileSystemState = fileSystemState;
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.err.println("Remove command has to be invoked with exactly 1 element");
        }
        String path = args[0];

        List<FileSystemNode> removedNodes = fileSystemState.buildPath(path);
        FileSystemNode removedNode = removedNodes.get(removedNodes.size() - 1);

        // Если удаляем папку, рекурсивно проходимся по ее чайлдам
        // на самом деле это можно и не делать, т.к. достаточно удалить ссылку на папку из родительской
        // директории и ее почистит сборщик мусора, но решение с явным удалением более корректно, т.к.
        // поведение должно быть явно описано в коде, а не завязываться на неочевидные вещи под капотом jvm
        if (removedNode instanceof Folder folder) {
            cleanUpFolder(folder);
        }
        ((Folder) removedNode.getParent()).removeChild(removedNode.getName());
    }

    @Override
    public String getName() {
        return "rm";
    }

    private void cleanUpFolder(Folder folder) {
        List<FileSystemNode> deleted = new ArrayList<>();
        for (FileSystemNode child : folder.getChildren().values()) {
            if (child instanceof Folder childFolder) {
                cleanUpFolder(childFolder);
            }
            deleted.add(child);
        }

        for (FileSystemNode deletedNode: deleted) {
            folder.removeChild(deletedNode.getName());
        }
    }
}
