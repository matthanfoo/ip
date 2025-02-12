package tasks;
import java.time.LocalDateTime;

public class Deadline extends Task {

    public Deadline(String description, LocalDateTime by) {
        super(description, by, LocalDateTime.of(1990, 01, 01, 00, 00, 00, 00));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dt1 + ")";
    }

    public String toCsvFormat() {
        return "D," + super.toCsvFormat();
    }
}