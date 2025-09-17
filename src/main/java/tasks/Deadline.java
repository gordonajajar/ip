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

    @Override
    public String toSaveString() {
        return "D" + " | " + isDone() + " | " + getDescription() + " | " + by;
    }

    public static Deadline fromSaveString(String line) throws EmptyDescriptionException {
        String[] input = line.split(DELIMITER);
        Deadline savedDeadline = new Deadline(input[2], input[3]);
        if (input[1].equals("true")) {
            savedDeadline.setDone();
        }
        return savedDeadline;
    }
}
