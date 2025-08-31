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

        while (true) {
            String line = in.nextLine();
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("    You said: " + line);
            System.out.println("-----------------------------------------------------------------------------");
            if (line.equals("bye")) {
                break;
            }
        }
    }


}
