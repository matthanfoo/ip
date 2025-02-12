package tasks;
import java.time.LocalDateTime;

public class Event extends Task {

    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description, start, end);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + dt1 + " to: " + dt2 + ")";
    }

    public String toCsvFormat() {
        return "E," + super.toCsvFormat();
    }
}