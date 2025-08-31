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
        String[] list = new String[100];
        int listIndex = 0;

        while (true) {
            line = in.nextLine();

            System.out.println("-----------------------------------------------------------------------------");
            if (line.equals("bye")) {
                System.out.println("Adios");
                break;
            } else if (line.equals("list")) {
                int idx = 0;
                for (int i = 0; i < listIndex; i++) {
                    System.out.println(i + 1 + ". " + list[i]);
                }
            } else {
                list[listIndex++] = line;
                System.out.println("    Added: " + line);
            }
            System.out.println("-----------------------------------------------------------------------------");
        }
    }


}
