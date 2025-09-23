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

public class TaskStorage {

    private final Path file;

    public TaskStorage(String filePath) {
        this.file = Paths.get(filePath);
    }

    public ArrayList<Task> loadTasks() throws IOException, EmptyDescriptionException {
        Path absolutePath = file.toAbsolutePath();
        if (!Files.exists(file)) {
            System.out.println("No old tasks found at " + absolutePath);
            return new ArrayList<>();
        }

        List<String> lines = Files.readAllLines(file);
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

    public List<Task> tasksOnDate(LocalDate date) throws EmptyDescriptionException, IOException {
        ArrayList<Task> tasks = loadTasks();
        List<Task> tasksOnDate = tasks.stream()
                .filter(t -> {
                    if (t instanceof Deadline) {
                        LocalDateTime dateTime = ((Deadline) t).getBy();
                        return dateTime.toLocalDate().equals(date);
                    }
                    return false;
                })
                .toList();
        return tasksOnDate;
    }

}
