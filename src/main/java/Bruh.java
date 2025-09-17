import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import exceptions.EmptyDescriptionException;
import exceptions.WrongTaskNumberException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

public class Bruh {

    public static final int MAX_TASKS_LENGTH = 100;

    public static void main(String[] args) {
        String logo = """
                Hello! I'm:
                 ____  ____  _   _ _   _\s
                | __ )|  _ \\| | | | | | |
                |  _ \\| |_) | | | | |_| |
                | |_) |  _ <| |_| |  _  |
                |____/|_| \\_\\\\___/|_| |_|\
                
                Type something:\s
                """;
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
        boolean isTaskListModified = false;
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
                Todo newToDo = new Todo(input[1]);
                tasks[Task.getNumberOfTasks() - 1] = newToDo;
                System.out.println("Added todo: \n" + newToDo + "\n" + "You now have " + Task.getNumberOfTasks() + " tasks.");
                isTaskListModified = true;
                break;
            case "deadline":
                String[] deadlineInput = input[1].split(" /by ", 2);
                Deadline newDeadline = new Deadline(deadlineInput[0], deadlineInput[1]);
                tasks[Task.getNumberOfTasks() - 1] = newDeadline;
                System.out.println("Added deadline: \n" + newDeadline + "\n" + "You now have " + Task.getNumberOfTasks() + " tasks.");
                isTaskListModified = true;
                break;
            case "event":
                String[] eventInput = input[1].split((" /from | /to "), 3);
                Event newEvent = new Event(eventInput[0], eventInput[1], eventInput[2]);
                tasks[Task.getNumberOfTasks() - 1] = newEvent;
                System.out.println("Added event: \n" + newEvent + "\n" + "You now have " + Task.getNumberOfTasks() + " tasks.");
                isTaskListModified = true;
                break;
            case "mark":
                Task taskToMark = getTaskByIndex(tasks, input[1]);
                taskToMark.setDone();
                System.out.println("Marked as done: " + taskToMark);
                isTaskListModified = true;
                break;
            case "unmark":
                Task taskToUnmark = getTaskByIndex(tasks, input[1]);
                taskToUnmark.setUndone();
                System.out.println("Marked as undone: " + taskToUnmark);
                isTaskListModified = true;
                break;
            default:
                System.out.println("Huh?...");
            }

            if (isTaskListModified) {
                saveTasksToDisk(tasks);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing arguments!");
        } catch (EmptyDescriptionException e) {
            System.out.println("Your description is empty, try again...");
        } catch (WrongTaskNumberException e) {
            System.out.println("You entered an invalid task number; choose tasks 1 to " + Task.getNumberOfTasks());
        } catch (IOException e) {
            System.out.println("Problem saving tasks to file " + e.getMessage());
        }
        return false;
    }

    private static Task getTaskByIndex(Task[] tasks, String indexString) throws WrongTaskNumberException {
        try {
            int index = Integer.parseInt(indexString) - 1;
            if (index < 0 || index >= Task.getNumberOfTasks()) throw new WrongTaskNumberException();
            return tasks[index];
        } catch (NumberFormatException e) {
            throw new WrongTaskNumberException();
        }
    }

    private static void saveTasksToDisk(Task[] tasks) throws IOException {
        String filePath = "./data/tasks.txt";
        File f = new File(filePath);

        // ensure parent directory exists
        File parent = f.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IOException("Could not create directory " + parent + ", check permission issues?");
        }

        try (FileWriter fw = new FileWriter(f)) {
            for (Task task : tasks) {
                fw.write(task.toSaveString());
                fw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Could not access " + filePath + " for writing :((");
            throw e;
        }
    }
}
