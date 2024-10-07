package files;

import java.text.SimpleDateFormat;
import java.util.Date;

public class File extends Node {
    private int sizeInBytes;
    private Date lastModified;

    public File(String name, int sizeInBytes, Date lastModified) {
        super(name);
        this.sizeInBytes = sizeInBytes;
        this.lastModified = lastModified;
    }

    // Return the size of the file
    @Override
    public int getSize() {
        return sizeInBytes;
    }

    // Getter for lastModified
    public Date getLastModified() {
        return lastModified;
    }

    // Provide HTML representation of the file
    @Override
    public String asHTML() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String modifiedDate = sdf.format(lastModified);
        return "<div><span>Name: " + getName() + "</span><br>" +
                "<span>Size: " + getSize() + " bytes</span><br>" +
                "<span>Last Modified: " + modifiedDate + "</span></div>";
    }
}

