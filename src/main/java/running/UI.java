package running;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class UI {

    protected Storage storage;
    protected TaskList taskList;
    public BufferedReader br;

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

    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    public void print(String s) {
        System.out.println(s);
        printLine();
    }

}
