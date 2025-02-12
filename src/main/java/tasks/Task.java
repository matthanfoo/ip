package tasks;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    protected String description;
    protected boolean isDone;
    protected LocalDateTime dt1;
    protected LocalDateTime dt2;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

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

    public LocalDateTime getDt1() {
        return dt1;
    }

    public String toCsvFormat() {
        String doneText = this.isDone ? "X" : " ";
        return description + "," + doneText + "," + dt1.format(dateTimeFormatter)+ "," + dt2.format(dateTimeFormatter);
    }


}