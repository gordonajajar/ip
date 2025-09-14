package tasks;

import exceptions.EmptyDescriptionException;

public class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) throws EmptyDescriptionException {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.getStatusIcon() + " " + super.getDescription() + " (from: " + from + " to: " + to + ")";
    }
}
