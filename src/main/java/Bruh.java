import java.sql.SQLOutput;
import java.util.Scanner;

public class Bruh {
    public static void main(String[] args) {
        String logo = "Hello! I'm:\n" +
                " ____  ____  _   _ _   _ \n" +
                "| __ )|  _ \\| | | | | | |\n" +
                "|  _ \\| |_) | | | | |_| |\n" +
                "| |_) |  _ <| |_| |  _  |\n" +
                "|____/|_| \\_\\\\___/|_| |_|" +
                "\n" +
                "Type something: \n";
        System.out.println(logo);

        Scanner in = new Scanner(System.in);
        String line;
        Task[] tasks = new Task[100];
        int taskIndex = 0;

        while (true) {
            line = in.nextLine();

            System.out.println("-----------------------------------------------------------------------------");
            if (line.equals("bye")) {
                System.out.println("Adios");
                break;
            } else if (line.equals("list")) {
                System.out.println("Here are your tasks: ");
                int idx = 0;
                for (int i = 0; i < taskIndex; i++) {
                    System.out.println(i + 1 + ". [" + tasks[i].getStatusIcon() + "]" + tasks[i].getDescription());
                }
            } else if (line.startsWith("mark ")) {
                int markIndex = -1 + Integer.parseInt(line.substring(5));
                tasks[markIndex].setDone(true);
                System.out.println("Marked as done: [X] " + tasks[markIndex].getDescription());
            } else if (line.startsWith("unmark ")) {
                int unmarkIndex = -1 + Integer.parseInt(line.substring(7));
                tasks[unmarkIndex].setDone(false);
                System.out.println("Marked as undone: [ ] " + tasks[unmarkIndex].getDescription());
            } else {
                tasks[taskIndex++] = new Task(line);
                System.out.println("    Added: " + line);
            }
            System.out.println("-----------------------------------------------------------------------------");
        }
    }


}
