package edu.gavrilov.fs.model;

import java.util.Objects;

public abstract class FileSystemNode {
    private final String name;
    private final FileSystemNode parent;

    public FileSystemNode(String name, FileSystemNode parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public FileSystemNode getParent() {
        return parent;
    }

    public abstract FileSystemNode copy();

    @Override
    public String toString() {
        return (this.parent == null ? "/" : this.parent + "/").replace("//", "/") + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSystemNode that = (FileSystemNode) o;
        return name.equals(that.name) && Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parent);
    }
}
