package tasks;

import exceptions.EmptyDescriptionException;

public class Todo extends Task {


    public Todo(String description) throws EmptyDescriptionException {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.getStatusIcon() + " " + super.getDescription();
    }

    @Override
    public String toSaveString() {
        return "T" + " | " + isDone() + " | " + getDescription();
    }

    public static Todo fromSaveString(String line) throws EmptyDescriptionException {
        String[] input = line.split(DELIMITER);
        Todo savedTodo = new Todo(input[2]);
        if (input[1].equals("true")) {
            savedTodo.setDone();
        }
        return savedTodo;
    }
}
