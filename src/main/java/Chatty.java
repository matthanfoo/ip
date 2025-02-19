import running.Parser;
import running.Storage;
import running.TaskList;

/**
 * Runs the bot through the UI object instantiated when the main function is run
 */
public class Chatty {

    private Storage storage;
    private TaskList tasks;
    private Parser parser;

    /**
     * use the default filePath to read in any stored in the file corresponding to the filePath into a Storage object
     * which is then read into a TaskList object and passed to the UI to use when interacting with the user
     */
    public Chatty() {
        storage = new Storage();
        tasks = new TaskList(storage.load());
        parser = new Parser();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return parser.execute(tasks, input);
    }

    /**
     * Save all working tasks before terminating
     */
    public void save() {
        storage.save(tasks);
    }

}
