package running;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest {
    @Test
    public void validateDate_validDateTrue(){
        assertEquals(Parser.validateDate("16-02-2025"), true);
    }

    @Test
    public void validateDate_invalidDateFalse(){
        assertEquals(Parser.validateDate("02-16-25 12:45"), false);
        assertEquals(Parser.validateDate("16/13/25"), false);
    }

    @Test
    public void validateTime_validTimeTrue(){
        assertEquals(Parser.validateDate("12:45"), true);
    }

    @Test
    public void validateTime_validTimeFalse(){
        assertEquals(Parser.validateDate("32:45"), false);
    }
    @Test
    public void parseMark_returnsDigit() {
        assertEquals(Parser.parseIndex("mark 3"), 3);
        assertEquals(Parser.parseIndex("unmark 31"), 31);
    }

    @Test
    public void parseTitle_returnsTitle() {
        assertEquals(Parser.parseTitle("/todo testtitle", "todo"), "testtitle");
        assertEquals(Parser.parseTitle("/todo twoword title", "todo"), "twoword title");
        assertEquals(Parser.parseTitle("/deadline twoword title /by date", "deadline"), "twoword title");
    }

    @Test
    public void readInputIntoIso_returnsIsoOrException() {

        try {
            assertEquals(Parser.readInputIntoIso("16-02-2025 12:00"), "16-02-2025 12:00");
            assertEquals(Parser.readInputIntoIso("16-02-2025"), "16-02-2025 00:00");
        } catch (Exception e) {
            System.out.println("Testing readInputIntoIso, expected true, got: " + e.getMessage());
        }

        assertThrows(Exception.class, () -> {Parser.readInputIntoIso("16-2-2025");});
        assertThrows(Exception.class, () -> {Parser.readInputIntoIso("16-22-2025");});
        assertThrows(Exception.class, () -> {Parser.readInputIntoIso("16-22-2025 1200");});
        assertThrows(Exception.class, () -> {Parser.readInputIntoIso("16-22-2025 25:00");});

    }


}
