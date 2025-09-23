package tasks;

import java.time.LocalDateTime;

import exceptions.EmptyDescriptionException;
import util.DateTimeUtil;

/**
 * Represents a Deadline task, which has a description, a completion status, and a due date/time.
 */
public class Deadline extends Task {

    /**
     * The date and time by which the task should be completed.
     */
    private final LocalDateTime by;

    /**
     * Returns the deadline date and time.
     *
     * @return LocalDateTime representing the deadline.
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Constructs a new Deadline with the given description and due date/time.
     *
     * @param description Description of the task.
     * @param by          Due date and time of the task.
     * @throws EmptyDescriptionException If the description is blank.
     */
    public Deadline(String description, LocalDateTime by) throws EmptyDescriptionException {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of the Deadline for display purposes.
     *
     * @return Display string including type, status, description, and formatted due date/time.
     */
    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + super.getDescription() + " (by: " + DateTimeUtil.prettyPrint(by) + ")";
    }

    /**
     * Returns a string suitable for saving the Deadline to a file.
     *
     * @return Save string including type, completion status, description, and formatted due date/time.
     */
    @Override
    public String toSaveString() {
        return "D" + " | " + isDone() + " | " + getDescription() + " | " + DateTimeUtil.formatForSave(by);
    }

    /**
     * Creates a Deadline object from a saved string.
     *
     * @param line String from storage representing a Deadline.
     * @return Deadline object reconstructed from the string.
     * @throws EmptyDescriptionException If the description in the string is blank.
     */
    public static Deadline fromSaveString(String line) throws EmptyDescriptionException {
        String[] input = line.split(DELIMITER);
        Deadline savedDeadline = new Deadline(input[2], DateTimeUtil.parseString(input[3]));
        if (input[1].equals("true")) {
            savedDeadline.setDone();
        }
        return savedDeadline;
    }
}
