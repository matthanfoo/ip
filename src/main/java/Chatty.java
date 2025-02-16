import tasks.*;
import running.*;

public class Chatty {

    private Storage storage;
    private TaskList tasks;
    private UI ui;

    public Chatty(String filePath) {
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
        new Chatty("ChattyData.csv").run();
    }
}