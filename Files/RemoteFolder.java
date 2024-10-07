package files;

import java.util.List;

public class RemoteFolder extends Folder {

    public RemoteFolder(String name) {
        super(name);
    }

    // Override getSize to return 0 for remote folders
    @Override
    public int getSize() {
        return 0;
    }

    // Override asHTML to represent a remote folder (in italics)
    @Override
    public String asHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<div style=\"font-style:italic;\"><span><b>Remote Folder: ").append(getName()).append("</b></span><br>");
        List<Node> children = getChildren();  // Now we can access the children list
        for (Node child : children) {
            html.append(child.asHTML()).append("<br>");
        }
        html.append("</div>");
        return html.toString();
    }
}
