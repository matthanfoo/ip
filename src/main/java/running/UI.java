package running;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * user interface sends and receives messages from the user and uses a Parser object to process input
 * and thus execute commands from the user
 */
public class UI {

    protected Storage storage;
    protected TaskList taskList;
    private BufferedReader br;

    /**
     * uses the default Storage and TaskList objects provided to it, and creates a new BufferedReader to start
     * reading user input
     * @param storage   Storage object passed in when the bot is started
     * @param taskList  TaskList object passed in when the bot is started
     */
    public UI(Storage storage, TaskList taskList) {
        this.storage = storage;
        this.taskList = taskList;
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * this function reads the next line from the reader and returns it as a string
     * @return  the next line from the reader
     * @throws  if the reader throws an exception
     */
    public String readCommand() throws IOException {
        // validate and convert command into text
        String command = this.br.readLine();
        return command;
    }


    /**
     * this function prints a line of hyphens to function as a divider between user input and bot response
     */
    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * this function prints the string passed to it as a reply to the user
     * @param   s   any string to be sent as a message to the user
     */
    public void print(String s) {
        System.out.println(s);
        printLine();
    }

}
