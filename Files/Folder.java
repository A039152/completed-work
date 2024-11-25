package files;

import java.util.ArrayList;
import java.util.List;

public class Folder extends Node {
    private List<Node> children;

    public Folder(String name) {
        super(name);
        children = new ArrayList<>();
    }

    // Add a child node (either a file or another folder)
    @Override
    public void add(Node child) {
        children.add(child);
    }

    // Return the total size of the folder (sum of all children's sizes)
    @Override
    public int getSize() {
        int totalSize = 0;
        for (Node child : children) {
            totalSize += child.getSize();
        }
        return totalSize;
    }

    // HTML representation of the folder and its children
    @Override
    public String asHTML() {
        StringBuilder html = new StringBuilder();
        html.append(String.format("<strong>%s</strong> (%d bytes)<ul>", name, getSize()));
        for (Node child : children) {
            html.append("<li>").append(child.asHTML()).append("</li>");
        }
        html.append("</ul>");
        return html.toString();
    }

    // Protected getter for the children list to allow access in subclasses
    protected List<Node> getChildren() {
        return children;
    }
}
