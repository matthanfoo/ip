import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Chatty {
    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello! I'm Chatty McChatface");
        System.out.println("What can I do for you?");
        String s = "";
        ArrayList<Task> userInputs = new ArrayList<Task>();

        while (true) {
            try {
                s = br.readLine();
                if (s.equals("bye") || s.equals("Bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    br.close();
                    break;
                } else if (s.equals("list")) {
                    int i = 0;
                    System.out.println("Here are the tasks in your list: ");
                    while (i != userInputs.size()) {
                        System.out.println(String.valueOf(i + 1) + ". " + userInputs.get(i));
                        i++;
                    }
                } else if (s.contains("mark")) {
                    Pattern p = Pattern.compile("([0-9]+$)");
                    Matcher m = p.matcher(s);
                    if (m.find()) {
                        System.out.println(m.group(1));
                        int i = Integer.parseInt(m.group(1)) - 1;
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
                            break;
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
                            break;
                        }

                        Pattern fromPattern = Pattern.compile("/from\s*(.*?)\s+/");
                        Matcher fromPatternMatcher = fromPattern.matcher(s);
                        String fromString = "";
                        if (fromPatternMatcher.find()) {
                            fromString = fromPatternMatcher.group(1);
                        } else {
                            System.out.println("Invalid from timing");
                        }

                        Pattern toPattern = Pattern.compile("/to\\s*(.*)");
                        Matcher toPatternMatcher = toPattern.matcher(s);
                        String toString = "";
                        if (toPatternMatcher.find()) {
                            toString = toPatternMatcher.group(1);
                        } else {
                            System.out.println("Invalid to timing");
                        }

                        Event e = new Event(eventTitle, fromString, toString);
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
                            break;
                        }

                        Pattern byPattern = Pattern.compile("/by\\s*(.*)");
                        Matcher byPatternMatcher = byPattern.matcher(s);
                        String byString = "";
                        if (byPatternMatcher.find()) {
                            byString = byPatternMatcher.group(1);
                        } else {
                            System.out.println("Invalid by timing");
                        }

                        Deadline d = new Deadline(deadlineTitle, byString);
                        userInputs.add(d);
                        System.out.println("Got it. I've added this task:\n  " + d);
                        System.out.println("Now you have " + userInputs.size() + " items in the list.");

                    } else {
                        System.out.println("Invalid item");
                    }

                }
            } catch (IOException ioe) {
                System.out.println(ioe);
            } finally {
                System.out.println("-----------------------------------------");
            }

        }
    }
}
