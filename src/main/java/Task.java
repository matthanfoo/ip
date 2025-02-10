import java.time.LocalDate;
import java.time.LocalDateTime;


public class Task {
    protected String description;
    protected boolean isDone;
    protected LocalDateTime dt1;
    protected LocalDateTime dt2;

    public Task(String description, LocalDateTime dt1, LocalDateTime dt2) {
        this.description = description;
        this.isDone = false;
        this.dt1 = dt1;
        this.dt2 = dt2;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String toString() {
        return " [" + this.getStatusIcon() + "] " + this.description;
    }

    public String toCsvFormat() {
        return "";
    }


}