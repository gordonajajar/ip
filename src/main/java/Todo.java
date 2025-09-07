public class Todo extends Task {

    public Todo(String description) {
        super(description);
        System.out.println("Added todo: \n" + this.toString() + ".\n" + "You now have " + Task.getNumberOfTasks() + " tasks.");
    }

    @Override
    public String toString() {
        return "[T]" + super.getStatusIcon() + " " + super.getDescription();
    }
}
