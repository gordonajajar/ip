package tasks;

import exceptions.EmptyDescriptionException;

public class Event extends Task {
    private final String from;
    private final String to;

    public Event(String description, String from, String to) throws EmptyDescriptionException {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.getStatusIcon() + " " + super.getDescription() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toSaveString() {
        return "E" + " | " + isDone() + " | " + getDescription() + " | " + from + " | " + to;
    }

    public static Event fromSaveString(String line) throws EmptyDescriptionException {
        String[] input = line.split(DELIMITER);
        Event savedEvent = new Event(input[2], input[3], input[4]);
        if (input[1].equals("true")) {
            savedEvent.setDone();
        }
        return savedEvent;
    }
}
