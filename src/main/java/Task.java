public class Task {

    private static int numberOfTasks = 0;

    private String description;
    private boolean isDone;

    public Task(String description) {
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

    public void setDescription(String description) {
        if (description.isEmpty()) {
            this.description = "Undefined task";
            return;
        }
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
