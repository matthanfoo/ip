package tasks;
import java.time.LocalDateTime;

/**
 * Deadline is a subclass of Task with a by datetime
 */
public class Deadline extends Task {

    public Deadline(String description, LocalDateTime by) {
        super(description, by, LocalDateTime.of(1990, 01, 01, 00, 00, 00, 00));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dt1.format(super.dateTimeFormatter) + ")";
    }

    @Override
    public String toCsvFormat() {
        return "D," + super.toCsvFormat();
    }

    @Override
    public Deadline createRecur(String recurType) throws IllegalArgumentException {
        return switch (recurType) {
        case "d" -> new Deadline(this.description, this.dt1.plusDays(1));
        case "w" -> new Deadline(this.description, this.dt1.plusWeeks(1));
        case "m" -> new Deadline(this.description, this.dt1.plusMonths(1));
        case "y" -> new Deadline(this.description, this.dt1.plusYears(1));
        default -> throw new IllegalArgumentException("Invalid recur type: " + recurType
                + " . Recur type must be (d)ay / (m)onth / (w)eek / (y)ear");
        };

    }
}
