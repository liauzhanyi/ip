import java.time.format.DateTimeParseException;

public class ZBot {
    private static final String SAVE_PATH = "../../../data/tasks.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public ZBot(String filepath) {
        ui = new Ui();
        storage = new Storage(SAVE_PATH);
        try {
            tasks = new TaskList(storage.load());
        } catch (ZBotException e) {
            ui.printLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.intro();
        storage.createFileIfNotExists();
        String input = ui.readCommand();
        while (!input.equals("bye")) {
            processInput(input, ui);
            input = ui.readCommand();
        }

        storage.save(tasks);
        ui.outro();
    }

    public static void main(String[] args) {
        new ZBot(SAVE_PATH).run();
    }

    public void processInput(String input, Ui ui) {
        if (input.equals("list")) {
            listTasks();
        } else if (input.startsWith("mark")) {
            markTask(input, ui);
        } else if (input.startsWith("unmark")) {
            unmarkTask(input, ui);
        } else if (input.startsWith("todo") ||
                input.startsWith("deadline") ||
                input.startsWith("event")) {
            addTask(input, ui);
        } else if (input.startsWith("delete")) {
            deleteTask(input, ui);
        } else {
            System.out.println("Invalid command.\n");
        }
    }

    public void addTask(String input, Ui ui) {
        Task task;
        String[] inputParts = input.split(" ", 2);

        try {
            if (inputParts[0].equals("deadline")) {
                String[] deadlineParts = inputParts[1].split(" /by ", 2);
                task = new Deadline(deadlineParts[0], Parser.parseDateTime(deadlineParts[1]));
            } else if (inputParts[0].equals("event")) {
                String[] eventParts = inputParts[1].split(" /from ", 2);
                String[] period = eventParts[1].split(" /to ", 2);
                task = new Event(eventParts[0],
                        Parser.parseDateTime(period[0]),
                        Parser.parseDateTime(period[1]));
            } else {
                task = new ToDo(inputParts[1]);
            }

            tasks.add(task);
            ui.printAddTaskMsg(task, tasks.size());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter a valid task format!\n");
        } catch (DateTimeParseException e) {
            System.out.println(
                    "Please enter a valid date and time format (dd/MM/yyyy HHmm, dd/MM/yyyy)!\n");
        }
    }

    public void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, tasks.get(i)));
        }
        System.out.println();
    }

    public void markTask(String input, Ui ui) {
        try {
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            tasks.get(taskNumber - 1).markAsDone();
            ui.printMarkTaskMsg(tasks.get(taskNumber - 1));
        } catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter a valid task number!\n");
        }
    }

    public void unmarkTask(String input, Ui ui) {
        try {
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            tasks.get(taskNumber - 1).markAsUndone();
            ui.printUnmarkTaskMsg(tasks.get(taskNumber - 1));
        } catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter a valid task number!\n");
        }
    }

    public void deleteTask(String input, Ui ui) {
        try {
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            Task task = tasks.remove(taskNumber - 1);
            ui.printDeleteTaskMsg(task, tasks.size());
        } catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter a valid task number!\n");
        }
    }
}
