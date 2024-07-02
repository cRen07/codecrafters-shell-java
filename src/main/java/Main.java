//When opening a new terminal when you open this project for the first time make sure the terminal is in the correct directory 
// PS C:\Users\Caleb Choi\Desktop\Projects\Shell Project (Java)\codecrafters-shell-java>
//If not use the command cd to change the directory to the correct one
//'cd codecrafters-shell-java' <-- USE THIS COMMAND TO GET TO THE CORRECT DIRECTORY AND TO BE ABLE TO USE GIT COMMANDS

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            String[] builtins = {"cd", "echo", "exit", "type"};
            String[] parts = input.split(" ");
            String command = parts[0];
            String[] arguments = Arrays.copyOfRange(parts, 1, parts.length);

            switch (command) {
                case "exit":
                    return;
                case "cd":
                break;
                case "echo":
                    StringBuilder sb = new StringBuilder();
                    for (String arg : arguments){
                        sb.append(arg).append(" ");
                    }
                    System.out.println(sb.toString().trim());
                    break;

                case "type":
                    if (arguments.length == 0) {
                        System.out.println("type: missing argument");
                    } else {
                        String argument = arguments[0];
                        if (Arrays.asList(builtins).contains(argument)) {
                            System.out.println(argument + " is a shell builtin");
                        } else {
                            System.out.println(argument + " is not a shell builtin");
                        }
                    }
                    break;
                default:
                    System.out.println(input + ": command not found");

            }
        }
    }
}