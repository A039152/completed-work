package files;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HiddenFile extends File {

    public HiddenFile(String name, int sizeInBytes, Date lastModified) {
        super(name, sizeInBytes, lastModified);
    }

    // Override asHTML to return a hidden file representation
    @Override
    public String asHTML() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String modifiedDate = sdf.format(getLastModified());  // Use the getter from File class
        return "<div style=\"color:gray;\"><span>Name: " + getName() + " <i>[hidden]</i></span><br>" +
                "<span>Size: " + getSize() + " bytes</span><br>" +
                "<span>Last Modified: " + modifiedDate + "</span></div>";
    }
}
