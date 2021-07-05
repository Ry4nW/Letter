package textEditor;

import java.nio.file.Path;
import java.util.List;

// Class {TextFile} allows us to store the textArea's content, save it to a file, load it from a file etc.
// Containing fields {files} and {content} to be accessed by other classes.
public class TextFile {

    private Path file;
    private List<String> content;

    public TextFile(Path file, List<String> content) {
        this.file = file;
        this.content = content;
    }

    public Path getFile() {
        return file;
    }

    public List<String> getContent() {
        return content;
    }

    public void setFile(Path path) { file = path; }
}
