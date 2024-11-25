package files;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class HiddenFile extends File {

    public HiddenFile(String name, int sizeInBytes, LocalDateTime lastModified) {
        super(name, sizeInBytes, lastModified);
    }
    @Override
    public String asHTML() {
        // Reuse File's asHTML() and wrap the output in a span with grey text and [hidden] tag.
        return String.format(
                "<span style='color: grey;'>%s <em>[hidden]</em></span>",
                super.asHTML()
        );
    }
}
