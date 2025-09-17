import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import java.nio.file.Files;
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

    public static final String filePath = "./data/tasks.txt";

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
        ArrayList<Task> tasks;

        File f = new File(filePath);
        try {
            tasks = loadTasksFromDisk(f);
        } catch (EmptyDescriptionException | IOException e) {
            System.out.println("Problem reading " + filePath + ".");
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

            if (parseInput(command, tasks, input)) return;

            System.out.println("-----------------------------------------------------------------------------");
        }
    }

    private static boolean parseInput(String command, ArrayList<Task> tasks, String[] input) {
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
            case "todo":
                Todo newToDo = new Todo(input[1]);
                tasks.add(newToDo);
                System.out.println("Added todo: \n" + newToDo + "\n" + "You now have " + tasks.size() + " tasks.");
                isTaskListModified = true;
                break;
            case "deadline":
                String[] deadlineInput = input[1].split(" /by ", 2);
                if (deadlineInput[1].isBlank()) {
                    throw new EmptyDescriptionException();
                }
                Deadline newDeadline = new Deadline(deadlineInput[0], deadlineInput[1]);
                tasks.add(newDeadline);
                System.out.println("Added deadline: \n" + newDeadline + "\n" + "You now have " + tasks.size() + " tasks.");
                isTaskListModified = true;
                break;
            case "event":
                String[] eventInput = input[1].split((" /from | /to "), 3);
                if (eventInput[1].isBlank() || eventInput[2].isBlank()) {
                    throw new EmptyDescriptionException();
                }
                Event newEvent = new Event(eventInput[0], eventInput[1], eventInput[2]);
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
                Task taskToRemove = getTaskByIndex(tasks, input[1]);
                System.out.println("Successfully removed task " + input[1] + ":\n" + taskToRemove.toString());
                tasks.remove(taskToRemove);
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
            if (tasks.isEmpty()) {
                System.out.println("You have no tasks");
            } else {
                System.out.println("You entered an invalid task number; choose tasks 1 to " + tasks.size());
            }
        } catch (IOException e) {
            System.out.println("Problem saving tasks to file " + e.getMessage());
        }
        return false;
    }

    private static Task getTaskByIndex(ArrayList<Task> tasks, String indexString) throws WrongTaskNumberException {
        try {
            int index = Integer.parseInt(indexString) - 1;
            if (index < 0 || index >= tasks.size()) throw new WrongTaskNumberException();
            return tasks.get(index);
        } catch (NumberFormatException e) {
            throw new WrongTaskNumberException();
        }
    }

    private static ArrayList<Task> loadTasksFromDisk(File f) throws IOException, EmptyDescriptionException {
        String absolutePath = f.getAbsolutePath();
        if (!f.exists()) {
            System.out.println("No old tasks found at " + absolutePath);
            return new ArrayList<>();
        }

        List<String> lines = Files.readAllLines(f.toPath());
        ArrayList<Task> tasks = new ArrayList<>();
        System.out.println("Loading tasks from " + absolutePath);
        for (String line : lines) {
            switch (line.charAt(0)) {
            case 'T':
                tasks.add(Todo.fromSaveString(line));
                break;
            case 'D':
                tasks.add(Deadline.fromSaveString(line));
                break;
            case 'E':
                tasks.add(Event.fromSaveString(line));
                break;
            default:
                System.out.println("Unknown line encountered in " + absolutePath);
            }
        }
        return tasks;
    }

    private static void saveTasksToDisk(ArrayList<Task> tasks) throws IOException {
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
