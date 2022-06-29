package edu.gavrilov.fs.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Folder extends FileSystemNode {

    // используем LinkedHashMap, чтобы сохранять порядок добавления чайлдов
    private final Map<String, FileSystemNode> children = new LinkedHashMap<>();

    public Folder(String name, FileSystemNode parent) {
        super(name, parent);
    }

    public void addChild(FileSystemNode child) {
        children.put(child.getName(), child);
    }

    public void removeChild(String name) {
        children.remove(name);
    }

    public Map<String, FileSystemNode> getChildren() {
        return children;
    }

    @Override
    public FileSystemNode copy() {
        final var copy = new Folder(getName(), getParent());
        for (FileSystemNode child: children.values()) {
            copy.addChild(child.copy());
        }
        return copy;
    }
}
