import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String[] builtins = {"echo", "exit", "type"};

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String command = parts[0];
            String[] arguments = Arrays.copyOfRange(parts, 1, parts.length);

            switch (command) {
                case "exit":
                    return;
                case "cd":
                    // Add your "cd" command logic here
                    break;
                case "echo":
                    // Concatenate the arguments
                    StringBuilder sb = new StringBuilder();
                    for (String arg : arguments) {
                        sb.append(arg).append(" ");
                    }
                    // Print the result
                    System.out.println(sb.toString().trim());
                    break;
                case "type":
                    if (arguments.length != 1) {
                        System.out.println("Usage: type <command>");
                        break;
                    }
                    String commandToCheck = arguments[0];
                    boolean isBuiltin = false;
                    for (String builtin : builtins) {
                        if (builtin.equals(commandToCheck)) {
                            isBuiltin = true;
                            break;
                        }
                    }
                    if (isBuiltin) {
                        System.out.println(commandToCheck + " is a shell builtin");
                    } else {
                        String path = System.getenv("PATH");
                        String[] pathDirs = path.split(System.getProperty("path.separator"));
                        boolean found = false;
                        for (String dir : pathDirs) {
                            File file = new File(dir, commandToCheck);
                            if (file.isFile() && file.canExecute()) {
                                System.out.println(commandToCheck + " is " + file.getAbsolutePath());
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println(commandToCheck + ": not found");
                        }
                    }
                    break;
                default:
                    System.out.println(input + ": command not found");
            }
        }
    }
}
