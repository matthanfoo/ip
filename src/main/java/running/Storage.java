package running;
import tasks.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Storage {

    public String filename;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Storage(String filename) {
        this.filename = filename;
    }

    /**
     * this function takes in a string array of items in a row downloaded from a csv, and creates a task with the relevant information
     * @param   row     a string array containing items corresponding to the columns in the csv for a row
     * @return          a task created with the relevant information
     * @throws Exception if the task is not either a todo, deadline, or an event
     */
    public Task readRowToTask(String[] row) throws Exception {
        Task t;
        if (row[0].equals("T")) {
            t = new Todo(row[1]);
            if (row[2].equals("X")) { t.mark(); }
        } else if (row[0].equals("E")) {
            t = new Event(row[1], LocalDateTime.parse(row[3], dateTimeFormatter), LocalDateTime.parse(row[3], dateTimeFormatter));
            if (row[2].equals("X")) { t.mark(); }
        } else if (row[0].equals("D")) {
            t = new Deadline(row[1], LocalDateTime.parse(row[3], dateTimeFormatter));
            if (row[2].equals("X")) { t.mark(); }
        } else {
            throw new Exception("Invalid task");
        }

        return t;
    }

    /**
     * this function converts a csv file of saved data into an ArrayList of Tasks
     * @return          an ArrayList of Tasks as read from the csv file database
     */
    public ArrayList<Task> load() {
        File file = new File(filename);
        ArrayList<Task> userInputs = new ArrayList<Task>();

        if (file.exists()) {
            System.out.println("File exists, reading contents...");

            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(","); // Splitting by comma
                    userInputs.add(readRowToTask(row));
                }
            } catch (Exception e) {
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

    /**
     * this function takes in a TaskList object and reads it into multiple lines in order to save into a csv file
     * @param tasks          a TaskList containing a list of tasks from the chatbot
     */
    public void save(TaskList tasks) {
        String fileName = "ChattyData.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            writer.println("Type,Title,Done,Date1,Date2");
            ArrayList<Task> taskList = tasks.getTasks();

            for (Task task : taskList) {
                writer.println(task.toCsvFormat());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}