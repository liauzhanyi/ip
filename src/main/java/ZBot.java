import java.util.Scanner;

public class ZBot {
    private static final Task[] tasks = new Task[100];
    private static int taskIndex = 0;

    public static void main(String[] args) {
        greet();

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                listTask();
            } else if (input.startsWith("mark")) {
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    markTaskAsDone(taskNumber);
                } catch (NullPointerException e) {
                    System.out.println("Task not found!\n");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid task number!\n");
                }
            } else if (input.startsWith("unmark")) {
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    markTaskAsUndone(taskNumber);
                } catch (NullPointerException e) {
                    System.out.println("Task not found!\n");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid task number!\n");
                }
            } else if (input.startsWith("todo") || input.startsWith("deadline")) {
                storeTask(input);
            } else {
                System.out.println("Invalid command.\n");
            }

            input = sc.nextLine();
        }

        sc.close();
        exit();
    }

    public static void greet() {
        System.out.println("Hello! I'm ZBot!");
        System.out.println("What can I do for you?\n");
    }

    public static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void storeTask(String input) {
        // inputParts[0] is the command
        // inputParts[1] is the description
        String[] inputParts = input.split(" ", 2);

        Task task = new ToDo(inputParts[1]);
        if (inputParts[0].equals("deadline")) {
            try {
                String[] deadlineParts = inputParts[1].split(" /by ", 2);
                task = new Deadline(deadlineParts[0], deadlineParts[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Please enter a valid deadline task format!\n");
                return;
            }
        }

        tasks[taskIndex] = task;
        taskIndex = (taskIndex + 1) % 100;

        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println(String.format("Now you have %d tasks in the list.\n", taskIndex));
    }

    public static void listTask() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] != null) {
                System.out.println(String.format("%d. %s", i + 1, tasks[i]));
            }
        }
        System.out.println();
    }

    public static void markTaskAsDone(int taskNumber) {
        tasks[taskNumber - 1].markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(tasks[taskNumber - 1]);
        System.out.println();
    }

    public static void markTaskAsUndone(int taskNumber) {
        tasks[taskNumber - 1].markAsUndone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(tasks[taskNumber - 1]);
        System.out.println();
    }
}
