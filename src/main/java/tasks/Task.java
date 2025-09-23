package tasks;

import exceptions.EmptyDescriptionException;

/**
 * Represents a generic task with a description and completion status.
 * Can be marked as done or undone, and supports conversion to a string
 * for display or saving purposes.
 */
public class Task {

    /**
     * Delimiter used in saved task strings.
     */
    public static final String DELIMITER = " \\| ";

    private String description;
    private boolean isDone;


    /**
     * Constructs a new Task with the given description.
     * Newly created tasks are initially not done.
     *
     * @param description Description of the task.
     * @throws EmptyDescriptionException If the description is blank.
     */
    public Task(String description) throws EmptyDescriptionException {
        this.setDescription(description);
        this.isDone = false;
    }

    /**
     * Returns the status icon representing completion.
     *
     * @return "[X]" if the task is done, "[ ]" otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    /**
     * Returns the status icon representing completion.
     *
     * @return "[X]" if the task is done, "[ ]" otherwise.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets the task's description.
     *
     * @param description New description for the task.
     * @throws EmptyDescriptionException If the description is blank.
     */
    public void setDescription(String description) throws EmptyDescriptionException {
        if (description.isBlank()) {
            throw new EmptyDescriptionException();
        }
        this.description = description;
    }

    /**
     * Checks whether the task is completed.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks the task as completed.
     */
    public void setDone() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void setUndone() {
        isDone = false;
    }

    /**
     * Returns a string representation of the task for display purposes.
     *
     * @return Task description.
     */
    @Override
    public String toString() {
        return getDescription();
    }

    /**
     * Returns a string suitable for saving to a file.
     * By default, this is just the task's description. Subclasses may override.
     *
     * @return String to save to persistent storage.
     */
    public String toSaveString() {
        return this.getDescription();
    }

}
