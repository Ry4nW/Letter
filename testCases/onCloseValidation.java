import org.junit.Before;
import org.junit.Test;
import testingCaseFiles.onCloseMethods;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class onCloseValidation {

    private String textArea;
    private ArrayList<String> onCloseSaveCheck;
    private String button;

    @Before
    public void setup() {
        textArea = "";
        onCloseSaveCheck = new ArrayList<>();
        button = "";
    }

    @Test
    public void methodTests() {

        // Accept all conditionals situation.

        textArea = "Henlo\nHenlo 2";
        onCloseSaveCheck.add("Henlo\nHenlo 2");
        button = "ok";

        assertEquals("Save initiated.", onCloseMethods.onClose(textArea, onCloseSaveCheck, button));

        // Accept first conditional but cancels the rest.

        button = "cancel";

        assertEquals("Exited!",  onCloseMethods.onClose(textArea, onCloseSaveCheck, button));

        // Expect an IndexOutOfBoundsException. (the event when the client tries to exit but has never saved their work during the session)

        onCloseSaveCheck.clear();

        assertEquals("Exited!", onCloseMethods.onClose(textArea, onCloseSaveCheck, button));

        // Expect an IndexOutOfBoundsException but with a client agreement to save their work.

        button = "ok";

        assertEquals("Save initiated from catch.", onCloseMethods.onClose(textArea, onCloseSaveCheck, button));

        // Arguments do not meet condition to try.

        textArea = "";

        assertEquals("Exited!", onCloseMethods.onClose(textArea, onCloseSaveCheck, button));
    }
}
