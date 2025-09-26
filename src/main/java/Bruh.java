import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import java.io.IOException;

import storage.TaskStorage;
import exceptions.EmptyDescriptionException;
import exceptions.WrongTaskNumberException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;
import util.DateTimeUtil;

/**
 * Main class for the task management application.
 * Handles user input, task creation, modification, deletion, and queries.
 * Loads tasks from a file on startup and saves tasks after modifications.
 */
public class Bruh {

    /**
     * File path for storing tasks.
     */
    public static final String filePath = "./data/tasks.txt";


    /**
     * Main entry point for the task application.
     * Loads tasks from storage, then continuously reads user commands from the console.
     *
     * @param args Command-line arguments (unused).
     */
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

        TaskStorage storage = new TaskStorage(filePath);
        ArrayList<Task> tasks;

        try {
            System.out.println("Loading tasks from " + filePath);
            tasks = storage.loadTasks();
        } catch (EmptyDescriptionException | IOException e) {
            System.out.println("Problem reading " + filePath);
            return;
        }

        while (true) {
            line = in.nextLine();

            System.out.println("-----------------------------------------------------------------------------");
            if (line.isBlank()) {
                System.out.println("Please enter something");
                continue;
            }
            String[] input = line.split(" ", 2);
            String command = input[0].toLowerCase();

            if (parseInput(storage, command, tasks, input)) return;

            System.out.println("-----------------------------------------------------------------------------");
        }
    }

    /**
     * Parses and executes a user command.
     *
     * @param storage TaskStorage instance for saving/loading tasks.
     * @param command The command string (e.g., "todo", "list").
     * @param tasks   Current list of tasks.
     * @param input   Full input split into command and arguments.
     * @return true if the application should exit (e.g., "bye" command), false otherwise.
     */
    private static boolean parseInput(TaskStorage storage, String command, ArrayList<Task> tasks, String[] input) {
        boolean isTaskListModified = false;
        try {
            switch (command) {
            case "bye":
                System.out.println("Adios");
                return true;
            case "list":
                if (tasks.isEmpty()) {
                    System.out.println("No tasks");
                } else {
                    System.out.println("Here are your tasks: ");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(i + 1 + ". " + tasks.get(i).toString());
                    }
                }
                break;
            case "find":
                String keyword = input[1].trim();
                printTasksByKeyword(storage, keyword);
                break;
            case "todo":
                Todo newToDo = new Todo(input[1].trim());
                tasks.add(newToDo);
                System.out.println("Added todo: \n" + newToDo + "\n" + "You now have " + tasks.size() + " tasks.");
                isTaskListModified = true;
                break;
            case "deadline":
                String[] deadlineInput = input[1].split(" /by ", 2);
                if (deadlineInput[1].isBlank()) {
                    throw new EmptyDescriptionException();
                }
                Deadline newDeadline = new Deadline(deadlineInput[0].trim(), DateTimeUtil.parseString(deadlineInput[1]));
                tasks.add(newDeadline);
                System.out.println("Added deadline: \n" + newDeadline + "\n" + "You now have " + tasks.size() + " tasks.");
                isTaskListModified = true;
                break;
            case "due":
                printTasksOnDate(storage, DateTimeUtil.parseString(input[1]));
                break;
            case "event":
                String[] eventInput = input[1].split((" /from | /to "), 3);
                if (eventInput[1].isBlank() || eventInput[2].isBlank()) {
                    throw new EmptyDescriptionException();
                }
                Event newEvent = new Event(eventInput[0].trim(), eventInput[1].trim(), eventInput[2].trim());
                tasks.add(newEvent);
                System.out.println("Added event: \n" + newEvent + "\n" + "You now have " + tasks.size() + " tasks.");
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
            case "delete":
                if (input[1].trim().equals("all")) {
                    System.out.println("Removing all tasks");
                    tasks.clear();
                    isTaskListModified = true;
                    break;
                }
                Task taskToRemove = getTaskByIndex(tasks, input[1]);
                System.out.println("Successfully removed task " + input[1] + ":\n" + taskToRemove.toString());
                tasks.remove(taskToRemove);
                isTaskListModified = true;
                break;
            default:
                System.out.println("Huh?...");
            }

            if (isTaskListModified) {
                try {
                    storage.saveTasks(tasks);
                } catch (IOException e) {
                    System.out.println("Failed to save tasks :(( " + e.getMessage());
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing arguments!");
        } catch (EmptyDescriptionException e) {
            System.out.println("An input field is empty, try again...");
        } catch (WrongTaskNumberException e) {
            if (tasks.isEmpty()) {
                System.out.println("You have no tasks");
            } else {
                System.out.println("You entered an invalid task number; choose tasks 1 to " + tasks.size());
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid dateTime format. Expected: dd/MM/yyyy HHmm (time optional)");
        } catch (IOException e) {
            System.out.println("Error getting tasks from data");
        }
        return false;
    }

    /**
     * Prints a list of tasks to the console.
     *
     * @param tasks List of tasks to print.
     */
    private static void printTasks(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
    }

    /**
     * Prints tasks that occur on the specified date.
     *
     * @param storage TaskStorage instance for querying tasks.
     * @param date    The date to filter tasks by.
     * @throws EmptyDescriptionException If a loaded task has an empty description.
     * @throws IOException               If there is an error reading tasks from storage.
     */
    private static void printTasksOnDate(TaskStorage storage, LocalDateTime date) throws EmptyDescriptionException, IOException {
        List<Task> tasks = storage.tasksOnDate(date.toLocalDate());
        if (tasks.isEmpty()) {
            System.out.println("No tasks with date " + DateTimeUtil.prettyPrint(date));
        } else {
            System.out.println("Printing tasks with date: " + DateTimeUtil.prettyPrint(date));
            printTasks(tasks);
        }
    }


    /**
     * Prints tasks whose descriptions contain the specified keyword.
     *
     * @param storage TaskStorage instance for querying tasks.
     * @param keyword Keyword to search for in task descriptions.
     * @throws EmptyDescriptionException If a loaded task has an empty description.
     * @throws IOException               If there is an error reading tasks from storage.
     */
    private static void printTasksByKeyword(TaskStorage storage, String keyword) throws EmptyDescriptionException, IOException {
        List<Task> tasks = storage.tasksByKeyword(keyword);
        if (tasks.isEmpty()) {
            System.out.println("No tasks with keyword " + keyword);
        } else {
            System.out.println("Printing tasks with keyword: " + keyword);
            printTasks(tasks);
        }

    }


    /**
     * Retrieves a task by its 1-based index in the list.
     *
     * @param tasks       List of tasks to search.
     * @param indexString 1-based index as a string.
     * @return Task at the specified index.
     * @throws WrongTaskNumberException If the index is invalid or out of range.
     */
    private static Task getTaskByIndex(ArrayList<Task> tasks, String indexString) throws WrongTaskNumberException {
        try {
            int index = Integer.parseInt(indexString) - 1;
            if (index < 0 || index >= tasks.size()) throw new WrongTaskNumberException();
            return tasks.get(index);
        } catch (NumberFormatException e) {
            throw new WrongTaskNumberException();
        }
    }

}
