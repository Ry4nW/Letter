import IO.IO;
import IO.IOResult;
import org.junit.Before;
import org.junit.Test;
import textEditor.TextFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class fileLoadValidation {

    private IO io;
    private TextFile textFile;
    private final String testFile = "testCases\\testingCaseFiles\\testing.txt";
    private final Path testPath = Paths.get(testFile);
    private File file;

    @Before
    public void setup() throws IOException {
        io = new IO();
        file = new File(testFile);
        textFile = new TextFile(Paths.get(testFile), Files.readAllLines(file.toPath()));
    }

    @Test
    public void correctAppendingOfText() throws IOException {

        // This section is just to test for our methods used in {onLoad}.

        FileWriter writer = new FileWriter(textFile.getFile().toString());
        List<String> content = Files.readAllLines(file.toPath());

        writer.write("");
        writer.write("Yo");
        writer.close();

        // file.toPath() returns a list, so we'll get the element in the first index.
        assertEquals("Yo", Files.readAllLines(file.toPath()).get(0));

        // Writes {testing.txt}'s original content in.
        for (String string : content) {
            writer.write(string);
        }

        writer.close();

        assertEquals("Yo", Files.readAllLines(file.toPath()).get(0));

    }

    @Test
    public void loadIOTest() throws IOException {

        // Testing of {.load} from class {IO}.

        // Making sure that data passed into {.load} doesn't deform in the process.

        IOResult<TextFile> ioResult = new IOResult<>(true, new TextFile(testPath, Files.readAllLines(testPath)));
        assertEquals(ioResult.getData().getContent(), io.load(testPath).getData().getContent());

        // Testing with a nullified file

        File file2 = new File("testCases\\testingCaseFiles\\nullifiedFile.txt");

        ioResult = new IOResult<>(true, new TextFile(file2.toPath(), Files.readAllLines(file2.toPath())));
        assertEquals(ioResult.getData().getContent(), io.load(file2.toPath()).getData().getContent());

        // Testing with a non .txt file with a non alphanumeric or keyboard character

        File inAppropriateFile = new File("README.md");

        ioResult = new IOResult<>(true, new TextFile(inAppropriateFile.toPath(), Files.readAllLines(inAppropriateFile.toPath())));
        assertEquals(ioResult.getData().getContent(), io.load(inAppropriateFile.toPath()).getData().getContent());

        // Testing with all keyboard characters, letters and numbers

        FileWriter fileWriter = new FileWriter(file2);

        fileWriter.write("1234567890");
        fileWriter.write("\nThe quick brown fox jumps over the lazy dog");
        fileWriter.write("\n./<>?;:\"'`!@#$%^&*()\\[\\]{}_+=|\\\\-");
        fileWriter.close();

        ioResult = new IOResult<>(true, new TextFile(file2.toPath(), Files.readAllLines(file2.toPath())));
        assertEquals(ioResult.getData().getContent(), io.load(file2.toPath()).getData().getContent());


    }

}
