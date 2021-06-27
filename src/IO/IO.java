package IO;

import textEditor.TextFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

// Our class for saving text into files and loading them back.
public class IO {

    // Requires: A textfile to write into.
    // Modifies: The textfile provided.
    // Effects: Writes into textFile, prints StackTrace if an IOException occurs.
    public void save(TextFile textFile) {
        try {
            Files.write(textFile.getFile(), textFile.getContent(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Requires: A file to load.
    // Modifies: Nothing.
    // Effects: Loads text from the file and returns a successful IOResult, prints StackTrace and returns an
    // unsuccessful IOResult if an IOException occurs.
    public IOResult<TextFile> load(Path file) {

        try {
            List<String> lines = Files.readAllLines(file);
            return new IOResult<>(true, new TextFile(file, lines));
        } catch (IOException e) {
            e.printStackTrace();
            return new IOResult<>(false, null);
        }

    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Terminates the process.
    public void close() {
        System.exit(0);
    }
}
