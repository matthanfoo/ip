package tasks;
import java.time.LocalDateTime;

/**
 * Todo is a subclass of Task with no datetime involved
 */
public class Todo extends Task {

    /**
     * Todo uses the superclass Task constructor to create an item with only a title,
     * and 01-01-1990 00:00 as a placeholder datetime
     * @param   description     The event description
     */
    public Todo(String description) {
        super(description, LocalDateTime.of(1990, 01, 01, 00, 00, 00, 00),
                LocalDateTime.of(1990, 01, 01, 00, 00, 00, 00));
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toCsvFormat() {
        return "T," + super.toCsvFormat();
    }
}
