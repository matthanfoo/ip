import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Chatty {

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
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



    public static String readInputIntoIso(String input) throws Exception { // need to throw exception
        // validates expected format either "dd-mm-yyyy" or "dd-mm-yyyy hh:mm" --> return "dd-mm-yyyy hh:mm"

        String[] dateitems = input.split(" ");
        String date = "";
        String time = "";
        if (dateitems.length == 2) {
            if (validateDate(dateitems[0])) {
                date = dateitems[0];
            } else {
                throw new Exception("invalid date, the accepted format is dd-mm-yyyy");
            }

            if (validateTime(dateitems[1])) {
                time = dateitems[1];
            } else {
                throw new Exception("invalid time, the accepted format is HH:mm (24-hour time)");
            }
        } else if (dateitems.length == 1) {
            if (validateDate(dateitems[0])) {
                date = dateitems[0];
                time = "00:00";
            } else {
                throw new Exception("invalid date, the accepted format is dd-mm-yyyy");
            }
        } else {
            throw new Exception("invalid date, the accepted format is dd-mm-yyyy");
        }

        return date + " " + time;
    }



    private static ArrayList<Task> openFileToInputs() {
        // open file if exists if not create new file
        String filename = "ChattyData.csv";
        File file = new File(filename);
        ArrayList<Task> userInputs = new ArrayList<Task>();

        if (file.exists()) {
            System.out.println("File exists, reading contents...");
            // read CSV into Task ArrayList

            try (BufferedReader br2 = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = br2.readLine()) != null) {
                    String[] row = line.split(","); // Splitting by comma
                    if (row[0].equals("T")) {
                        Todo t = new Todo(row[1]);
                        if (row[2].equals("X")) { t.mark(); }
                        userInputs.add(t);
                    } else if (row[0].equals("E")) {
                        Event e = new Event(row[1], LocalDateTime.parse(row[3], dateTimeFormatter), LocalDateTime.parse(row[3], dateTimeFormatter));
                        if (row[2].equals("X")) { e.mark(); }
                        userInputs.add(e);
                    } else if (row[0].equals("D")) {
                        Deadline d = new Deadline(row[1], LocalDateTime.parse(row[3], dateTimeFormatter));
                        if (row[2].equals("X")) { d.mark(); }
                        userInputs.add(d);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the file.");
                e.printStackTrace();
            }

        } else {
            System.out.println("File does not exist, creating new CSV...");

            try (FileWriter writer = new FileWriter(filename)) {
//                writer.append("Type, Title, Done, Date1, Date2\n"); // CSV Header
                System.out.println("CSV file written successfully.");
            } catch (IOException e) {
                System.out.println("Error writing to file.");
                e.printStackTrace();
            }
        }

        return userInputs;
    }

    private static void readInputsToFile(ArrayList<Task> inputs) {
        String fileName = "ChattyData.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            writer.println("Type,Title,Done,Date1,Date2");

            for (Task task : inputs) {
                writer.println(task.toCsvFormat());
            }

            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello! I'm Chatty McChatface");
        System.out.println("What can I do for you?");

        ArrayList<Task> userInputs = openFileToInputs();

        try {
            String s = "";
            while (s != null) {
                s = br.readLine();
                System.out.println("____________________________________________________________");
                if (s.equals("bye") || s.equals("Bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    br.close();
                    break;
                } else if (s.equals("list")) {
                    int i = 0;
                    System.out.println("Here are the tasks in your list:");
                    while (i != userInputs.size()) {
                        System.out.println(String.valueOf(i + 1) + ". " + userInputs.get(i));
                        i++;
                    }
                } else if (s.equals("today")) {
                    for (Task task : userInputs) {

                    }
                } else if (s.contains("mark")) {
                    Pattern p = Pattern.compile("([0-9]+$)");
                    Matcher m = p.matcher(s);
                    if (m.find()) {
                        int i = Integer.parseInt(m.group(1)) - 1;
                        if (i < userInputs.size()) {
                            Task task = userInputs.get(i);
                            if (s.contains("unmark")) {
                                task.unmark();
                                System.out.println("OK, I've marked this task as not done yet:");
                                System.out.println("   " + task);
                            } else if (s.contains("mark")) {
                                task.mark();
                                System.out.println("Nice! I've marked this task as done:");
                                System.out.println("   " + task);
                            }
                        } else {
                            System.out.println("Invalid item");
                        }
                    } else {
                        System.out.println("Invalid item");
                    }
                } else if (s.contains("delete")) {
                    Pattern p = Pattern.compile("([0-9]+$)");
                    Matcher m = p.matcher(s);
                    if (m.find()) {
                        int i = Integer.parseInt(m.group(1)) - 1;
                        if (i < userInputs.size()) {
                            Task task = userInputs.get(i);
                            System.out.println("Noted. I've removed this task:");
                            System.out.println("   " + task);
                            userInputs.remove(i);
                            System.out.println("Now you have " + userInputs.size() + " items in the list.");
                        } else {
                            System.out.println("Invalid item");
                        }
                    } else {
                        System.out.println("No such item");
                    }

                } else {
                    if (s.contains("todo")) {
                        Pattern titlePattern = Pattern.compile("todo\\s*(.*)");
                        Matcher titlePatternMatcher = titlePattern.matcher(s);
                        String todoTitle = "";
                        if (titlePatternMatcher.find()) {
                            todoTitle = titlePatternMatcher.group(1);
                        } else {
                            System.out.println("Invalid title");
                            continue;
                        }

                        Todo t = new Todo(todoTitle);
                        userInputs.add(t);
                        System.out.println("Got it. I've added this task:\n  " + t);
                        System.out.println("Now you have " + userInputs.size() + " items in the list.");
                    } else if (s.contains("event")) {
                        Pattern titlePattern = Pattern.compile("event\s*(.*?)\s+/");
                        Matcher titlePatternMatcher = titlePattern.matcher(s);
                        String eventTitle = "";
                        if (titlePatternMatcher.find()) {
                            eventTitle = titlePatternMatcher.group(1);
                        } else {
                            System.out.println("Invalid title");
                            continue;
                        }

                        Pattern fromPattern = Pattern.compile("/from\s*(.*?)\s+/");
                        Matcher fromPatternMatcher = fromPattern.matcher(s);
                        String fromString = "";
                        if (fromPatternMatcher.find()) {
                            fromString = fromPatternMatcher.group(1);
                            try {
                                fromString = readInputIntoIso(fromString);
                            } catch (Exception e){
                                System.out.println(e);
                                continue;
                            }
                        } else {
                            System.out.println("Invalid from timing");
                            continue;
                        }

                        Pattern toPattern = Pattern.compile("/to\\s*(.*)");
                        Matcher toPatternMatcher = toPattern.matcher(s);
                        String toString = "";
                        if (toPatternMatcher.find()) {
                            toString = toPatternMatcher.group(1);
                            try {
                                toString = readInputIntoIso(toString);
                            } catch (Exception e){
                                System.out.println(e);
                                continue;
                            }
                        } else {
                            System.out.println("Invalid to timing");
                            continue;
                        }

                        Event e = new Event(eventTitle, LocalDateTime.parse(fromString, dateTimeFormatter), LocalDateTime.parse(toString, dateTimeFormatter));
                        userInputs.add(e);
                        System.out.println("Got it. I've added this task:\n  " + e);
                        System.out.println("Now you have " + userInputs.size() + " items in the list.");
                    } else if (s.contains("deadline")) {
                        Pattern titlePattern = Pattern.compile("deadline\s*(.*?)\s+/");
                        Matcher titlePatternMatcher = titlePattern.matcher(s);
                        String deadlineTitle = "";
                        if (titlePatternMatcher.find()) {
                            deadlineTitle = titlePatternMatcher.group(1);
                        } else {
                            System.out.println("Invalid title");
                            continue;
                        }

                        Pattern byPattern = Pattern.compile("/by\\s*(.*)");
                        Matcher byPatternMatcher = byPattern.matcher(s);
                        String byString = "";
                        if (byPatternMatcher.find()) {
                            byString = byPatternMatcher.group(1);
                            try {
                                byString = readInputIntoIso(byString);
                            } catch (Exception e){
                                System.out.println(e);
                                continue;
                            }
                        } else {
                            System.out.println("Invalid by timing");
                            continue;
                        }

                        Deadline d = new Deadline(deadlineTitle, LocalDateTime.parse(byString, dateTimeFormatter));
                        userInputs.add(d);
                        System.out.println("Got it. I've added this task:\n  " + d);
                        System.out.println("Now you have " + userInputs.size() + " items in the list.");

                    } else {
                        System.out.println("Invalid item");
                        continue;
                    }
                }
            }
            readInputsToFile(userInputs);
        } catch (IOException e) {
            System.out.println("input error");
        }

    }
}