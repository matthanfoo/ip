import java.time.LocalDateTime;

public class Todo extends Task {

    public Todo(String description) {
        super(description, LocalDateTime.of(1990, 01, 01, 00, 00, 00, 00), LocalDateTime.of(1990, 01, 01, 00, 00, 00, 00));
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public String toCsvFormat() {
        String doneText = this.isDone ? "X" : " ";
        return "T," + description + "," + doneText + ", , ";
    }
}