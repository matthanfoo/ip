package running;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser uses its main function (execute) to break down a string of user input into specific commands and details
 * to be passed into other functions and returns a signal to the user interface (UI) whether
 * to continue accepting input
 */
public class Parser {
    public Parser() {}


    /**
     * this function takes in a string representing a date and checks if it is in dd-mm-yyyy format and
     * if the date, month, and year are within reasonable values so that it can be used to create
     * a valid date time object
     * @param   date    a string representing only the date of a datetime input
     * @return          a boolean if the date provided is formatted correctly
     */
    public static boolean validateDate(String date) {

        String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    /**
     * this function takes in a string representing a time and checks if it is in HH:mm format and
     * if the hour and minute are within reasonable values so that it can be used to create
     * a valid date time object
     * @param   time    a string representing only the time of a datetime input
     * @return          a boolean if the time provided is formatted correctly
     */
    public static boolean validateTime(String time) {

        String regex = "^([01][0-9]|2[0-3]):[0-5][0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);

        return matcher.matches();
    }

    /**
     * this function takes in a string containing "mark", attempts to extract the string following the string "mark",
     * and tries to convert this string to an integer that can be used to index the item indicated
     * @param   s       a string that contains mark (and ideally a valid integer representing an index)
     * @return          a non-negative index if a valid integer is found in the command
     *                  and -1 if the input is not valid and an integer cannot be found
     */
    public static int parseMark(String s) {
        Pattern p = Pattern.compile("([0-9]+$)");
        Matcher m = p.matcher(s);
        int markIndex = -1;
        if (m.find()) {
            markIndex = Integer.parseInt(m.group(1)) - 1;
        }
        return markIndex;
    }

    /**
     * this function takes in a string containing a full task creation command and extracts the title/name of the task
     * @param   s           a string representing the full command to create a task
     * @param   taskType    an integer representing the task the user is trying to create
     *                      (1 - todo, 2 - event, 3 - deadline)
     * @return              a string representing the title/name of the task the user is trying to create <br>
     *                      if no title is found, it returns an empty string ""
     */
    public static String parseTitle(String s, int taskType) {
        String type = taskType == 1 ? "todo" : (taskType == 2 ? "event" : "deadline");
        Pattern titlePattern = Pattern.compile(type + "\\s+([^/]+)");
        Matcher titlePatternMatcher = titlePattern.matcher(s);
        return titlePatternMatcher.find() ? titlePatternMatcher.group(1).trim() : "";
    }

    /**
     * this function takes in any string and a regex pattern and extracts the first sequence that matches the pattern
     * @param   s       any string of text
     * @param   regex   a regex pattern for search
     * @return          the first sequence in s that is matched by regex, <br>
     *                  if no matches are found it returns an empty string
     */
    public static String parseRegex(String s, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.find() ? m.group(1) : "";
    }

    /**
     * this function takes in a possible date-time string and attempts to validate it and
     * convert it into a valid datetime format string according to "dd-mm-yyyy HH:mm"
     * @param   input   a string identified as a possible representation of a datetime
     * @return          a valid datetime formatted string according to "dd-mm-yyyy HH:mm"
     * @throws  Exception if the date or time provided were not properly formatted or invalid
     */
    public static String readInputIntoIso(String input) throws Exception {
        // need to throw exception
        // validates expected format either "dd-mm-yyyy" or "dd-mm-yyyy hh:mm" --> return "dd-mm-yyyy hh:mm"

        String[] dateitems = input.split(" ");
        String date = "";
        String time = "";
        if (dateitems.length == 2) {
            if (validateDate(dateitems[0])) {
                date = dateitems[0];
            } else {
                throw new Exception("invalid date, the accepted format is dd-mm-yyyy, input: " + input);
            }

            if (validateTime(dateitems[1])) {
                time = dateitems[1];
            } else {
                throw new Exception("invalid time, the accepted format is HH:mm (24-hour time), input: " + input);
            }
        } else if (dateitems.length == 1) {
            if (validateDate(dateitems[0])) {
                date = dateitems[0];
                time = "00:00";
            } else {
                throw new Exception("invalid date, the accepted format is dd-mm-yyyy, input: " + input);
            }
        } else {
            throw new Exception("invalid date, the accepted format is dd-mm-yyyy, input: " + input);
        }

        return date + " " + time;
    }

    /**
     * this function handles a command that intends to create a task
     * @param   command     a command identified by the parser execute function as trying to create a task
     * @param   tasks       a TaskList holding all current events that new tasks can be added to
     * @param   taskType    an integer representing the task the user is trying to create
     *                      (1 - todo, 2 - event, 3 - deadline)
     * @return              the output (acknowledgement or error) from the creation of a Task
     */
    public static String handleTaskCommand(String command, TaskList tasks, int taskType) {
        String title = parseTitle(command, taskType);
        String printText;

        if (title.equals("")) {
            return "Invalid task title";
        }

        switch (taskType) {
        case 1:
            printText = tasks.createTodo(title);
            break;

        case 2:
            String fromString = parseRegex(command, "/from\\s*(.*?)\\s+/");
            String toString = parseRegex(command, "/to\\s*(.*)");
            try {
                fromString = readInputIntoIso(fromString);
                toString = readInputIntoIso(toString);
                printText = tasks.createEvent(title, fromString, toString);
            } catch (Exception e) {
                printText = e.getMessage();
            }
            break;

        case 3:
            String byString = parseRegex(command, "/by\\s*(.*)");
            try {
                byString = readInputIntoIso(byString);
                printText = tasks.createDeadline(title, byString);
            } catch (Exception e) {
                printText = e.getMessage();
            }
            break;

        default:
            return "Invalid task title";
        }

        return printText;
    }


    /**
     * this function takes in a command, parses it to identify the action to be taken, executes the action,
     * then returns a reply to the main method to send/display to the user
     * @param   tasks   the current list of tasks
     * @param   command the input read from the user interface
     * @return          false if the user would like to exit, and true otherwise
     */

    public String execute(TaskList tasks, String command) {

        String printText = "";
        if (command.equalsIgnoreCase("bye")) {
            return "bye";
        } else if (command.equalsIgnoreCase("list")) {
            printText = tasks.list();
        } else if (command.equalsIgnoreCase("today")) {
            printText = tasks.today();
        } else if (command.contains("unmark")) {
            int markIndex = parseMark(command);
            printText = markIndex < 0 ? "Invalid input" : tasks.unmarkTask(markIndex);
        } else if (command.contains("mark")) {
            int markIndex = parseMark(command);
            printText = markIndex < 0 ? "Invalid input" : tasks.markTask(markIndex);
        } else if (command.contains("delete")) {
            int markIndex = parseMark(command);
            printText = markIndex < 0 ? "Invalid input" : tasks.deleteTask(markIndex);
        } else if (command.contains("find")) {
            String findText = parseRegex(command, "find\s*(.*)");
            printText = tasks.find(findText);
        } else if (command.contains("todo")) {
            handleTaskCommand(command, tasks, 1);
        } else if (command.contains("event")) {
            handleTaskCommand(command, tasks, 2);
        } else if (command.contains("deadline")) {
            handleTaskCommand(command, tasks, 3);
        } else {
            printText = "Invalid command";
        }
        return printText;
    }
}
