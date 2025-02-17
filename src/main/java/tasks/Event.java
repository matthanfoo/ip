package tasks;
import java.time.LocalDateTime;

/**
 * Event is a subclass of Task with a from and to datetime
 */
public class Event extends Task {

    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description, start, end);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + dt1 + " to: " + dt2 + ")";
    }

    @Override
    public String toCsvFormat() {
        return "E," + super.toCsvFormat();
    }
}

