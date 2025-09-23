package tasks;

import exceptions.EmptyDescriptionException;


/**
 * Represents a Todo task, which has only a description and completion status.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo with the given description.
     *
     * @param description Description of the todo task.
     * @throws EmptyDescriptionException If the description is blank.
     */
    public Todo(String description) throws EmptyDescriptionException {
        super(description);
    }

    /**
     * Returns a string representation of the Todo for display purposes.
     *
     * @return Display string including type, status, and description.
     */
    @Override
    public String toString() {
        return "[T]" + super.getStatusIcon() + " " + super.getDescription();
    }

    /**
     * Returns a string suitable for saving the Todo to a file.
     *
     * @return Save string including type, completion status, and description.
     */
    @Override
    public String toSaveString() {
        return "T" + " | " + isDone() + " | " + getDescription();
    }

    /**
     * Creates a Todo object from a saved string.
     *
     * @param line String from storage representing a Todo.
     * @return Todo object reconstructed from the string.
     * @throws EmptyDescriptionException If the description in the string is blank.
     */
    public static Todo fromSaveString(String line) throws EmptyDescriptionException {
        String[] input = line.split(DELIMITER);
        Todo savedTodo = new Todo(input[2]);
        if (input[1].equals("true")) {
            savedTodo.setDone();
        }
        return savedTodo;
    }
}
