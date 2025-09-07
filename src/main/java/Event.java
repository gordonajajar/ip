public class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        System.out.println("Added event: \n" + this.toString() + ".\n" + "You now have " + Task.getNumberOfTasks() + " tasks.");
    }

    @Override
    public String toString() {
        return "[E]" + super.getStatusIcon() + " " + super.getDescription() + " (from: " + from + " to: " + to + ")";
    }
}
