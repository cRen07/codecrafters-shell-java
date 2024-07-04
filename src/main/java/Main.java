//When opening a new terminal when you open this project for the first time make sure the terminal is in the correct directory 
// PS C:\Users\Caleb Choi\Desktop\Projects\Shell Project (Java)\codecrafters-shell-java>
//If not use the command cd to change the directory to the correct one
//'cd codecrafters-shell-java' <-- USE THIS COMMAND TO GET TO THE CORRECT DIRECTORY AND TO BE ABLE TO USE GIT COMMANDS

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String[] builtins = {"echo", "exit", "type", "cd", "pwd"};

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
                    if (arguments.length != 1) {
                        System.out.println("Usage: cd <directory>");
                    } else {
                        String newDir = arguments[0];
                        String homeDir = System.getenv("HOME");

                        if (newDir.equals("~")) {
                            newDir = homeDir;
                        } else if (newDir.startsWith("~" + File.separator)) {
                            newDir = homeDir + newDir.substring(1);
                        }

                        File dir = new File(newDir);
                        try {
                            String canonicalPath = dir.getCanonicalPath();
                            if (new File(canonicalPath).isDirectory()) {
                                System.setProperty("user.dir", canonicalPath);
                            } else {
                                System.out.println("cd: " + arguments[0] + ": No such file or directory");
                            }
                        } catch (IOException e) {
                            System.out.println("cd: " + arguments[0] + ": No such file or directory");
                        }
                    }
                    break;
                case "echo":
                    StringBuilder sb = new StringBuilder();
                    for (String arg : arguments) {
                        sb.append(arg).append(" ");
                    }
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
                case "pwd":
                    String currentDirectory = System.getProperty("user.dir");
                    System.out.println(currentDirectory);
                    break;
                default:
                    try {
                        ProcessBuilder pb = new ProcessBuilder(parts);
                        pb.inheritIO();
                        Process process = pb.start();
                        process.waitFor();
                    } catch (IOException e) {
                        System.out.println(command + ": command not found");
                    } catch (InterruptedException e) {
                        System.out.println(command + ": execution interrupted");
                    }
            }
        }
    }
}

