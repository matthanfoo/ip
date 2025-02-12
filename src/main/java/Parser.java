import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public Parser() {}

    public static boolean validateDate(String date) {

        String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    public static boolean validateTime(String time) {

        String regex = "^([01][0-9]|2[0-3]):[0-5][0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);

        return matcher.matches();
    }

    public static int parseMark(String s) {
        Pattern p = Pattern.compile("([0-9]+$)");
        Matcher m = p.matcher(s);
        int markIndex = -1;
        if (m.find()) {
            markIndex = Integer.parseInt(m.group(1)) - 1;
        }
        return markIndex;
    }

    public static String parseTitle(String s, String type) {
        Pattern titlePattern = Pattern.compile(type + "\s*(.*)");
        Matcher titlePatternMatcher = titlePattern.matcher(s);
        return titlePatternMatcher.find() ? titlePatternMatcher.group(1) : "";
    }

    public static String parseRegex(String s, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.find() ? m.group(1) : "";
    }


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

    public boolean execute(TaskList tasks, String command, UI ui) {

        String printText = "";
        if (command.equalsIgnoreCase("bye")) {
            return false;
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
        } else if (command.contains("todo")) {
            String todoTitle = parseTitle(command, "todo");
            printText = todoTitle.equals("") ? "Invalid task title" : tasks.createTodo(todoTitle);
        } else if (command.contains("event")) {
            String eventTitle = parseTitle(command, "event");
            if (eventTitle.equals("")) {
                printText = "Invalid event title";
            } else {
                String fromString = parseRegex(command, "/from\s*(.*?)\s+/");
                String toString = parseRegex(command, "/to\\s*(.*)");
                try {
                    fromString = readInputIntoIso(fromString);
                    toString = readInputIntoIso(toString);
                    printText = tasks.createEvent(eventTitle, fromString, toString);
                } catch (Exception e){
                    printText = e.getMessage();
                }
            }
        } else if (command.contains("deadline")) {
            String deadlineTitle = parseTitle(command, "deadline");
            if (deadlineTitle.equals("")) {
                printText = "Invalid deadline title";
            } else {
                String byString = parseRegex(command, "/by\\s*(.*)");
                try {
                    byString = readInputIntoIso(byString);
                    printText = tasks.createDeadline(deadlineTitle, byString);
                } catch (Exception e){
                    printText = e.getMessage();
                }
            }
        } else {
            printText = "Invalid command";
        }
        ui.print(printText);
        return true;
    }

}
