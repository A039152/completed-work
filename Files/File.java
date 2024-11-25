package files;

import java.time.LocalDateTime;
import java.util.Date;

public class File extends Node {
    private int sizeInBytes;
    private LocalDateTime lastModified;

    public File(String name, int sizeInBytes, LocalDateTime lastModified) {
        super(name);
        this.sizeInBytes = sizeInBytes;
        this.lastModified = lastModified;
    }

    // Return the size of the file
    @Override
    public int getSize() {
        return sizeInBytes;
    }

   @Override
   public String asHTML() {
       return String.format(
               "<strong>%s</strong> (%d bytes, last modified %s)",
               name, sizeInBytes, lastModified
       );
   }
}

