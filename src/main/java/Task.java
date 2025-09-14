public class Task {

    private static int numberOfTasks = 0;

    private String description;
    private boolean isDone;

    public Task(String description) throws EmptyDescriptionException {
        this.setDescription(description);
        this.isDone = false;
        numberOfTasks++;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    public static int getNumberOfTasks() {
        return numberOfTasks;
    }

    public static void setNumberOfTasks(int numberOfTasks) {
        Task.numberOfTasks = numberOfTasks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws EmptyDescriptionException {
        if (description.isBlank()) {
            throw new EmptyDescriptionException();
        }
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone() {
        isDone = true;
        System.out.println("Marked as done: [X] " + this.getDescription());
    }

    public void setUndone() {
        isDone = false;
        System.out.println("Marked as undone: [ ] " + this.getDescription());
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
