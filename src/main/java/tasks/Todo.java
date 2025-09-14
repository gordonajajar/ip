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
}
