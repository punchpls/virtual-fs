package edu.gavrilov.fs.model;

public class File extends FileSystemNode {
    public File(String name, FileSystemNode parent) {
        super(name, parent);
    }

    @Override
    public FileSystemNode copy() {
        return new File(getName(), getParent());
    }
}
