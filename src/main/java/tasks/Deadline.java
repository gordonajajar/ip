package tasks;

import java.time.LocalDateTime;

import exceptions.EmptyDescriptionException;
import util.DateTimeUtil;

public class Deadline extends Task {

    private final LocalDateTime by;

    public LocalDateTime getBy() {
        return by;
    }

    public Deadline(String description, LocalDateTime by) throws EmptyDescriptionException {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + super.getDescription() + " (by: " + DateTimeUtil.prettyPrint(by) + ")";
    }

    @Override
    public String toSaveString() {
        return "D" + " | " + isDone() + " | " + getDescription() + " | " + DateTimeUtil.formatForSave(by);
    }

    public static Deadline fromSaveString(String line) throws EmptyDescriptionException {
        String[] input = line.split(DELIMITER);
        Deadline savedDeadline = new Deadline(input[2], DateTimeUtil.parseString(input[3]));
        if (input[1].equals("true")) {
            savedDeadline.setDone();
        }
        return savedDeadline;
    }
}
