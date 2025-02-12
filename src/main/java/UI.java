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
