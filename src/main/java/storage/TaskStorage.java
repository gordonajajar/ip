package storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import exceptions.EmptyDescriptionException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

/**
 * Manages persistent storage of Task objects in a file.
 * Tasks can be loaded from or saved to a file, and queried by date or keyword.
 */
public class TaskStorage {

    private final Path file;

    /**
     * Constructs a TaskStorage instance with the specified file path.
     *
     * @param filePath Relative path to the file where tasks are stored.
     */
    public TaskStorage(String filePath) {
        this.file = Paths.get(filePath);
    }

    /**
     * Loads tasks from the storage file.
     * <p>
     * If the file does not exist, an empty list is returned. Tasks are parsed
     * based on their type prefix ('T' for Todo, 'D' for Deadline, 'E' for Event).
     *
     * @return List of tasks loaded from the file.
     * @throws IOException               If there is an error reading the file.
     * @throws EmptyDescriptionException If a task in the file has an empty description.
     */
    public ArrayList<Task> loadTasks() throws IOException, EmptyDescriptionException {
        Path absolutePath = file.toAbsolutePath();
        if (!Files.exists(file)) {
            System.out.println("No old tasks found at " + absolutePath);
            return new ArrayList<>();
        }

        List<String> lines = Files.readAllLines(file);
        ArrayList<Task> tasks = new ArrayList<>();
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


    /**
     * Saves the given list of tasks to the storage file.
     * <p>
     * Creates the parent directory if it does not exist. Each task is written
     * using its toSaveString representation.
     *
     * @param tasks List of tasks to save.
     * @throws IOException If there is an error writing to the file.
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {

        // ensure parent directory exists
        Path parent = file.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        try (FileWriter fw = new FileWriter(file.toFile())) {
            for (Task task : tasks) {
                fw.write(task.toSaveString());
                fw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Could not access " + file.toAbsolutePath() + " for writing :((");
            throw e;
        }
    }

    /**
     * Returns the tasks that are due on the specified date.
     *
     * @param date The date to filter tasks by.
     * @return List of tasks that are due on the given date.
     * @throws IOException               If there is an error reading from the file.
     * @throws EmptyDescriptionException If a loaded task has an empty description.
     */
    public List<Task> tasksOnDate(LocalDate date) throws EmptyDescriptionException, IOException {
        ArrayList<Task> tasks = loadTasks();
        return tasks.stream()
                .filter(t -> {
                    if (t instanceof Deadline) {
                        LocalDateTime dateTime = ((Deadline) t).getBy();
                        return dateTime.toLocalDate().equals(date);
                    }
                    return false;
                })
                .toList();
    }

    /**
     * Returns the tasks whose descriptions contain the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     * @return List of tasks containing the keyword.
     * @throws IOException               If there is an error reading from the file.
     * @throws EmptyDescriptionException If a loaded task has an empty description.
     */
    public List<Task> tasksByKeyword(String keyword) throws EmptyDescriptionException, IOException {
        ArrayList<Task> tasks = loadTasks();
        return tasks.stream()
                .filter(t -> t.getDescription().contains(keyword))
                .toList();
    }

}
