import java.util.Scanner;

public class Bruh {

    public static final int MAX_TASKS_LENGTH = 100;

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
        Task[] tasks = new Task[MAX_TASKS_LENGTH];

        while (true) {
            line = in.nextLine();

            System.out.println("-----------------------------------------------------------------------------");
            if (line.isEmpty()) {
                System.out.println("Please enter something");
                continue;
            }
            String[] input = line.split(" ", 2);
            String command = input[0].toLowerCase();

            if (parseInput(command, tasks, input)) return;

            System.out.println("-----------------------------------------------------------------------------");
        }
    }

    private static boolean parseInput(String command, Task[] tasks, String[] input) {
        try {
            switch (command) {
            case "bye":
                System.out.println("Adios");
                return true;
            case "list":
                if (Task.getNumberOfTasks() == 0) {
                    System.out.println("No tasks");
                } else {
                    System.out.println("Here are your tasks: ");
                    for (int i = 0; i < Task.getNumberOfTasks(); i++) {
                        System.out.println(i + 1 + ". " + tasks[i].toString());
                    }
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
                getTaskByIndex(tasks, input[1]).setDone();
                break;
            case "unmark":
                getTaskByIndex(tasks, input[1]).setUndone();
                break;
            default:
                System.out.println("Huh?...");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing arguments!");
        } catch (EmptyDescriptionException e) {
            System.out.println("Your description is empty, try again...");
        } catch (WrongTaskNumberException e) {
            System.out.println("You entered an invalid task number; choose tasks 1 to " + Task.getNumberOfTasks());
        }
        return false;
    }

    private static Task getTaskByIndex(Task[] tasks, String indexString) throws WrongTaskNumberException {
        int index = Integer.parseInt(indexString) - 1;
        if (index < 0 || index >= Task.getNumberOfTasks()) throw new WrongTaskNumberException();
        return tasks[index];
    }
}
