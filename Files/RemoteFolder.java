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

    @Override
    public String asHTML() {
        // Wrap the Folder's HTML output in <em> tags for italicized text.
        return String.format("<em>%s</em>", super.asHTML());
    }
}
