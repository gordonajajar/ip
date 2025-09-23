package tasks;

import exceptions.EmptyDescriptionException;

/**
 * Represents an Event task, which has a description, completion status,
 * start time, and end time (as strings).
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Constructs a new Event with description, start time, and end time.
     *
     * @param description Description of the event.
     * @param from        Start time of the event.
     * @param to          End time of the event.
     * @throws EmptyDescriptionException If the description is blank.
     */
    public Event(String description, String from, String to) throws EmptyDescriptionException {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the Event for display purposes.
     *
     * @return Display string including type, status, description, start time, and end time.
     */
    @Override
    public String toString() {
        return "[E]" + super.getStatusIcon() + " " + super.getDescription() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns a string suitable for saving the Event to a file.
     *
     * @return Save string including type, completion status, description, start time, and end time.
     */
    @Override
    public String toSaveString() {
        return "E" + " | " + isDone() + " | " + getDescription() + " | " + from + " | " + to;
    }

    /**
     * Creates an Event object from a saved string.
     *
     * @param line String from storage representing an Event.
     * @return Event object reconstructed from the string.
     * @throws EmptyDescriptionException If the description in the string is blank.
     */
    public static Event fromSaveString(String line) throws EmptyDescriptionException {
        String[] input = line.split(DELIMITER);
        Event savedEvent = new Event(input[2], input[3], input[4]);
        if (input[1].equals("true")) {
            savedEvent.setDone();
        }
        return savedEvent;
    }
}
