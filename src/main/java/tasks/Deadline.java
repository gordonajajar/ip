package tasks;

import exceptions.EmptyDescriptionException;

public class Deadline extends Task {

    private String by;

    public Deadline(String description, String by) throws EmptyDescriptionException {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + super.getDescription() + " (by: " + by + ")";
    }
}
