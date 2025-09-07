import java.sql.SQLOutput;
import java.util.Scanner;

public class Bruh {
    public static void main(String[] args) {
        String logo = "Hello! I'm:\n" +
                " ____  ____  _   _ _   _ \n" +
                "| __ )|  _ \\| | | | | | |\n" +
                "|  _ \\| |_) | | | | |_| |\n" +
                "| |_) |  _ <| |_| |  _  |\n" +
                "|____/|_| \\_\\\\___/|_| |_|" +
                "\n" +
                "Type something: \n";
        System.out.println(logo);

        Scanner in = new Scanner(System.in);
        String line;
        Task[] tasks = new Task[100];

        while (true) {
            line = in.nextLine();

            System.out.println("-----------------------------------------------------------------------------");

            if (line.isEmpty()) {
                System.out.println("Please enter something.");
                continue;
            }
            String[] input = line.split(" ", 2);
            String command = input[0].toLowerCase();

            if (parseInput(command, tasks, input)) return;

            System.out.println("-----------------------------------------------------------------------------");
        }
    }

    private static boolean parseInput(String command, Task[] tasks, String[] input) {
        switch (command) {
        case "bye":
            System.out.println("Adios");
            return true;
        case "list":
            System.out.println("Here are your tasks: ");
            for (int i = 0; i < Task.getNumberOfTasks(); i++) {
                System.out.println(i + 1 + ". " + tasks[i].toString());
            }
            break;
        case "todo":
            tasks[Task.getNumberOfTasks()] = new Todo(input[1]);
            break;
        case "deadline":
            String[] deadlineInput = input[1].split(" /by ", 2);
            tasks[Task.getNumberOfTasks()] = new Deadline(deadlineInput[0], deadlineInput[1]);
            break;
        case "event":
            String[] eventInput = input[1].split((" /from | /to "), 3);
            tasks[Task.getNumberOfTasks()] = new Event(eventInput[0], eventInput[1], eventInput[2]);
            break;
        case "mark":
            int markIndex = -1 + Integer.parseInt(input[1]);
            tasks[markIndex].setDone(true);
            System.out.println("Marked as done: [X] " + tasks[markIndex].getDescription());
            break;
        case "unmark":
            int unmarkIndex = -1 + Integer.parseInt(input[1]);
            tasks[unmarkIndex].setDone(false);
            System.out.println("Marked as undone: [ ] " + tasks[unmarkIndex].getDescription());
            break;
        }
        return false;
    }

}
