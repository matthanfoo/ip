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
        return "[E]" + super.toString() + " (from: " + dt1.format(super.dateTimeFormatter) + " to: "
                + dt2.format(super.dateTimeFormatter) + ")";
    }

    @Override
    public String toCsvFormat() {
        return "E," + super.toCsvFormat();
    }

    @Override
    public Event createRecur(String recurType) throws IllegalArgumentException {
        return switch (recurType) {
        case "d" -> new Event(this.description, this.dt1.plusDays(1), this.dt2.plusDays(1));
        case "w" -> new Event(this.description, this.dt1.plusWeeks(1), this.dt2.plusWeeks(1));
        case "m" -> new Event(this.description, this.dt1.plusMonths(1), this.dt2.plusMonths(1));
        case "y" -> new Event(this.description, this.dt1.plusYears(1), this.dt2.plusYears(1));
        default -> throw new IllegalArgumentException("Invalid recur type: " + recurType
                + " . Recur type must be (d)ay / (m)onth / (w)eek / (y)ear");
        };

    }
}

