public class Ui {

    public void intro() {
        System.out.println("Hello! I'm ZBot!");
        System.out.println("What can I do for you?\n");
    }

    public void outro() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void printAddTaskMsg(Task task, int size) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.\n");
    }

    public void printDeleteTaskMsg(Task task, int size) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.\n");
    }

    public void printMarkTaskMsg(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        System.out.println();
    }

    public void printUnmarkTaskMsg(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        System.out.println();
    }
}
