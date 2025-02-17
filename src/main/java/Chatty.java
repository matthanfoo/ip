import running.Parser;
import running.Storage;
import running.TaskList;
import running.UI;

/**
 * Runs the bot through the UI object instantiated when the main function is run
 */
public class Chatty {

    private static final String filePath = "ChattyData.csv";
    private Storage storage;
    private TaskList tasks;
    private UI ui;

    /**
     * use the default filePath to read in any stored in the file corresponding to the filePath into a Storage object
     * which is then read into a TaskList object and passed to the UI to use when interacting with the user
     */
    public Chatty() {
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
        ui = new UI(storage, tasks);
    }

    /**
     * this function initialises a new Parser object and starts uses the user interface to welcome the user
     * to the chatbot and read any further input until the user chooses to exit the chatbot
     */
    public void run() {

        Parser parser = new Parser();
        boolean running = true;
        try {
            ui.print("Hello! I'm Chatty McChatface");
            ui.print("What can I do for you?");
            while (running) {
                String command = ui.readCommand();
                ui.printLine();
                running = parser.execute(tasks, command, ui);
            }
            storage.save(tasks);
            ui.print("Data saved successfully. Goodbye!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ui.printLine();
        }
    }

    public static void main(String[] args) {
        new Chatty().run();
    }
}
