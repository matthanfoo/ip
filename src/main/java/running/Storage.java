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
     * this function reads the data stored in the database and converts each row into a Task object to store
     * in an ArrayList of Tasks that can be read into a TaskList object
     * @return          an ArrayList of Tasks
     */
    public ArrayList<Task> load() {
        File file = new File(filename);
        ArrayList<Task> userInputs = new ArrayList<Task>();

        if (file.exists()) {
            System.out.println("File exists, reading contents...");
            // read CSV into Task ArrayList

            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = br.readLine()) != null) {
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

    /**
     * this function reads each Task in the TaskList into a format that can be saved as a csv and writes or overwrites
     * to the file to save the data permanently in the user directory
     * @param   tasks   a TaskList
     */
    public void save(TaskList tasks) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, false))) {
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