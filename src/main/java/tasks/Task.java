package tasks;

import exceptions.EmptyDescriptionException;

public class Task {

    public static final String DELIMITER = " \\| ";

    private String description;
    private boolean isDone;

    public Task(String description) throws EmptyDescriptionException {
        this.setDescription(description);
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
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
    }

    public void setUndone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    public String toSaveString() {
        return this.getDescription();
    }

}
