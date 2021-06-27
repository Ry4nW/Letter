package textEditor;

import java.nio.file.Path;
import java.util.List;

public class TextFile {

    private Path file;
    private List<String> content;

    public void Textfile(Path file, List<String> content) {
        this.file = file;
        this.content = content;
    }
}
