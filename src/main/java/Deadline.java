public class Deadline extends Task {

    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        System.out.println("Added deadline: \n" + this.toString() + ".\n" + "You now have " + Task.getNumberOfTasks() + " tasks.");
    }

    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + super.getDescription() + " (by: " + by + ")";
    }
}
