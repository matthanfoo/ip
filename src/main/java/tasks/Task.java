package tasks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Task represents any item in the list, and holds information about the task description, completion, and timings
 */
public class Task {

    protected static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    protected String description;
    protected boolean isDone;
    protected LocalDateTime dt1;
    protected LocalDateTime dt2;

    /**
     * creates a task object with the provided title and datetimes and
     * sets isDone to false by default as tasks entered are by default incomplete
     */
    public Task(String description, LocalDateTime dt1, LocalDateTime dt2) {
        this.description = description;
        this.isDone = false;
        this.dt1 = dt1;
        this.dt2 = dt2;
    }

    /**
     * this function returns the completion status of a Task as a string
     * @return  "X" is the task is done, "" if the task is not
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * this function marks an item as completed
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * this function marks an item as not completed
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * this function converts a task into a readable string
     * @return  task details as a readbale string
     */
    public String toString() {
        return " [" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * this function returns the first date of a Task (none for todo, deadline for deadline, start for event)
     * this datetime object is used for identifying if the task is occurring today, and for sorting (not implemented)
     * @return  DateTime object representing the datetime tagged to the event
     */
    public LocalDateTime getDt1() {
        return dt1;
    }

    public String getDescription() {
        return description;
    }

    /**
     * this function converts the task details into a string that can be saved in the csv file
     * @return  task details as comma-separated string
     */
    public String toCsvFormat() {
        String doneText = this.isDone ? "X" : " ";
        return description + "," + doneText + "," + dt1.format(dateTimeFormatter) + "," + dt2.format(dateTimeFormatter);
    }

    /**
     * this function creates a new Task with dates shifted forward by the specified recurrence interval
     * @param   recurType   either d/w/m/y representing the recurrence interval (day/week/month/year)
     * @return              a new Task with new dates
     */
    public Task createRecur(String recurType) throws IllegalArgumentException {
        return switch (recurType) {
        case "d" -> new Task(this.description, this.dt1.plusDays(1), this.dt2.plusDays(1));
        case "w" -> new Task(this.description, this.dt1.plusWeeks(1), this.dt2.plusWeeks(1));
        case "m" -> new Task(this.description, this.dt1.plusMonths(1), this.dt2.plusMonths(1));
        case "y" -> new Task(this.description, this.dt1.plusYears(1), this.dt2.plusYears(1));
        default -> throw new IllegalArgumentException("Invalid recur type: " + recurType
                + " . Recur type must be (d)ay / (m)onth / (w)eek / (y)ear");
        };

    }

}
