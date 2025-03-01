package running;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

/**
 * TaskList is a class that contains an underlying ArrayList of Task objects, with methods to edit and filter the list
 * of tasks and convert the list into readable/printable strings
 */
public class TaskList {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * this function converts all the tasks stored into a printable, numerated format
     * @return          a formatted, numerated string of all the tasks
     */
    public String list() {

        String result = "";
        int i = 0;
        if (!tasks.isEmpty()) {
            result = "Here are the tasks in your list:";
            while (i != tasks.size()) {
                result += "\n" + String.valueOf(i + 1) + ". " + tasks.get(i);
                i++;
            }
        } else {
            result = "There are no tasks in your list";
        }

        return result;
    }

    /**
     * this function identifies all tasks that occur / are due today and
     * converts all the tasks stored into a printable, numerated format
     * @return          a formatted, numerated string of all the tasks with date matching the current date
     */
    public String today() {
        String result = "";
        ArrayList<Task> todayTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task.getDt1().toLocalDate().equals(LocalDateTime.now().toLocalDate())) {
                todayTasks.add(task);
            }
        }
        if (!todayTasks.isEmpty()) {
            result = "Here are your tasks for today: ";
            for (Task task : todayTasks) {
                result += task + "\n";
            }
        } else {
            result = "No tasks for today!";
        }

        return result;
    }

    /**
     * this function creates a Todo object using the provided title
     * @param   title   the title identified by the Parser
     * @return                  an acknowledgement of the task creation along with task details
     */
    public String createTodo(String title) {
        Todo t = new Todo(title);
        tasks.add(t);
        return "Got it. I've added this task:\n" + t + "\nNow you have " + tasks.size() + " items in the list.";
    }

    /**
     * this function creates a Deadline object using the provided title and string representing the deadline
     * @param   deadlineTitle   the title identified by the Parser
     * @param   byString        the formatted datetime string representing the deadline for the task
     * @return                  an acknowledgement of the task creation along with task details
     */
    public String createDeadline(String deadlineTitle, String byString) {
        Deadline d = new Deadline(deadlineTitle, LocalDateTime.parse(byString, dateTimeFormatter));
        tasks.add(d);
        return "Got it. I've added this task:\n" + d + "\nNow you have " + tasks.size() + " items in the list.";
    }

    /**
     * this function creates an Event object using the provided title and
     * strings representing the start and end time of the event
     * @param   eventTitle  the title identified by the Parser
     * @param   fromString  the formatted datetime string representing the starttime for the task
     * @param   toString    the formatted datetime string representing the endtime for the task
     * @return              an acknowledgement of the task creation along with task details
     */
    public String createEvent(String eventTitle, String fromString, String toString) {
        Event e = new Event(eventTitle, LocalDateTime.parse(fromString, dateTimeFormatter),
                LocalDateTime.parse(toString, dateTimeFormatter));
        tasks.add(e);
        return "Got it. I've added this task:\n" + e + "\nNow you have " + tasks.size() + " items in the list.";
    }

    /**
     * this function ensures the item to be marked as completed is in the list,
     * if it is, it marks the event as completed, or else it informs the user the item does not exist
     * @param   markIndex   an integer representing the index of the item to be marked as completed
     * @return              an acknowledgement of the task completion or the task not existing
     */
    public String markTask(int markIndex) {
        if (markIndex < tasks.size()) {
            Task task = tasks.get(markIndex);
            task.mark();
            return ("OK, I've marked this task as done:\n   " + task);
        } else {
            return "Invalid index: " + markIndex;
        }
    }

    /**
     * this function ensures the item to be marked as not completed is in the list,
     * if it is, it marks the event as not completed, or else it informs the user the item does not exist
     * @param   markIndex   an integer representing the index of the item to be marked as not completed
     * @return              an acknowledgement of the task de-completion or the task not existing
     */
    public String unmarkTask(int markIndex) {
        if (markIndex < tasks.size()) {
            Task task = tasks.get(markIndex);
            task.unmark();
            return ("OK, I've marked this task as not done yet:\n   " + task);
        } else {
            return "Invalid index: " + markIndex;
        }
    }

    /**
     * this function ensures the item to be deleted is in the list,
     * if it is, it marks the event as deleted, or else it informs the user the item does not exist
     * @param   delIndex   an integer representing the index of the item to be deleted
     * @return             an acknowledgement of the task deletion and number of tasks remaining
     *                     or information that the task does not exist
     */
    public String deleteTask(int delIndex) {
        if (delIndex < tasks.size()) {
            Task task = tasks.get(delIndex);
            tasks.remove(delIndex);
            return "Noted. I've removed this task:\n   " + task
                    + "\n Now you have " + tasks.size() + " items in the list.";
        } else {
            return "Invalid index: " + delIndex;
        }
    }

    /**
     * this function takes in a string findText and finds all tasks
     * containining findText (case-insensitive) in its description and returns a readbale list as a string
     * @param   findText    a string containing target text to search for
     * @return              a list of all tasks containing findText
     */

    public String find(String findText) {
        assert !findText.equals("") : "empty findText field";
        String result = "";
        ArrayList<Task> findTasks = new ArrayList<Task>();

        for (Task task : tasks) {
            if (Pattern.compile(Pattern.quote(findText), Pattern.CASE_INSENSITIVE)
                    .matcher(task.getDescription()).find()) {
                findTasks.add(task);
            }
        }

        if (!findTasks.isEmpty()) {
            result = "Here are the matching tasks in your list: ";
            for (Task task : findTasks) {
                result += "\n" + task;
            }
        } else {
            result = "No tasks matching your search were found.";
        }

        return result;
    }


    /**
     * this function abstracts the checking of a valid recur type (day/month/week/year)
     * @param   recurType    a string containing a potentially valid recurType
     * @return               true if recurType is either d, w, m, or y and false otherwise
     */
    public boolean recurTypeCheck(String recurType) {
        return recurType.equals("d") || recurType.equals("w") || recurType.equals("m") || recurType.equals("y");
    }


    /**
     * this function recurs a specific Task with a specified interval for a specified number of times
     * @param   recurIndex   an integer representing the index of the item to be recurred
     * @param   recurType    a string containing a potentially valid recurType
     * @param   recurCount   an integer representing the number of times to recur the event for
     * @return               either a acknowledgement of successful recurrence creation or error message
     */
    public String recur(int recurIndex, String recurType, int recurCount) {
        if (recurIndex >= tasks.size() || recurIndex < 0 || !recurTypeCheck(recurType)) {
            return recurIndex >= tasks.size() || recurIndex < 0 ? "Invalid index: " + recurIndex
                    : "Invalid recur type: " + recurType + "\nRecur type must be (d)ay / (m)onth / (w)eek / (y)ear";
        }

        if (recurIndex < tasks.size()) {

            Task task = tasks.get(recurIndex);

            if (task instanceof Todo) {
                return "Cannot recur a Todo as it has no date attached";
            }

            for (int i = 0; i < recurCount; i++) {
                task = task.createRecur(recurType);
                tasks.add(task);
            }


            String recurDetail = switch (recurType) {
            case "d" -> "daily";
            case "w" -> "weekly";
            case "m" -> "monthly";
            case "y" -> "yearly";
            default -> "";
            };

            return String.format("Successfully recurred \"%s\" %s for %d occurrences.",
                    task.getDescription(), recurDetail, recurCount);
        }

        return "";
    }

    /**
     * this function returns the underlying ArrayList of Tasks
     * @return  an ArrayList of Tasks stored in the TaskList
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }





}
