import IO.IO;
import org.junit.Before;
import org.junit.Test;
import textEditor.TextFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class fileSaveValidation {

    private IO io;
    private TextFile textFile;
    private final String testFile = "testCases\\testingCaseFiles\\testing.txt";
    private final Path testPath = Paths.get(testFile);

    @Before
    public void setup() throws IOException {
        io = new IO();
        File file = new File(testFile);
        textFile = new TextFile(Paths.get(testFile), Files.readAllLines(file.toPath()));
    }

    @Test
    public void correctWritingOfText() throws IOException {

        // Test for our methods used in {onSave}.

        FileWriter writer = new FileWriter(textFile.getFile().toString(), false);

        writer.write("Henlo");
        writer.close();

        assertEquals("Henlo", Files.readAllLines(testPath).get(0));

        writer = new FileWriter("testCases\\testingCaseFiles\\nullifiedFile.txt", false);

        // Testing for an empty input.
        writer.write("");
        writer.close();

        assertEquals(0, Files.readAllLines(Paths.get("testCases\\testingCaseFiles\\nullifiedFile.txt")).size());

    }

    @Test
    public void saveIOTest() throws IOException {

        // Testing of {.save} from class {IO}.

        // Making sure that data passed into {.load} doesn't deform and writes properly.

        textFile.getContent().add("Henlo 2");
        io.save(textFile);
        assertEquals("Henlo 2", Files.readAllLines(testPath).get(1));

        // Testing with a nullified file

        textFile.getContent().clear();
        io.save(textFile);
        assertEquals(0, Files.readAllLines(testPath).size());

        // Testing with a non .txt file with a non alphanumeric or keyboard character

        File file = new File("README.md");
        TextFile README = new TextFile(testPath, Files.readAllLines(file.toPath()));

        io.save(README);

        System.out.println(README.getContent());
        assertEquals(Files.readAllLines(file.toPath()), Files.readAllLines(testPath));

        // Testing with all keyboard characters, letters and numbers

        README.getContent().clear();
        README.getContent().add("1234567890");
        README.getContent().add("The quick brown fox jumps over the lazy dog");
        README.getContent().add("./<>?;:\"'`!@#$%^&*()\\[\\]{}_+=|\\\\-");

        io.save(README);

        assertEquals(Files.readAllLines(Paths.get("testCases\\testingCaseFiles\\allCharacters.txt")), Files.readAllLines(testPath));

    }
}
