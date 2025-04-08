import java.io.*;
import java.util.*;

class Task {
    String description;
    String priority;
    String dueDate;

    public Task(String description, String priority, String dueDate) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return description + " | Priority: " + priority + " | Due: " + dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }
}

public class TaskManagementSystem {
    static final String FILE_NAME = "tasks.txt";
    static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addTask(scanner);
                case 2 -> updateTask(scanner);
                case 3 -> deleteTask(scanner);
                case 4 -> viewTasks();
                case 5 -> viewTasksByPriority(scanner);
                case 6 -> System.out.println("Exiting system.");
                default -> System.out.println("Invalid option, try again.");
            }
        } while (choice != 6);

        saveTasks();
    }

    private static void showMenu() {
        System.out.println("\n--- Task Management System ---");
        System.out.println("1. Add Task");
        System.out.println("2. Update Task");
        System.out.println("3. Delete Task");
        System.out.println("4. View All Tasks");
        System.out.println("5. View Tasks by Priority");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter task priority (High, Medium, Low): ");
        String priority = scanner.nextLine();

        System.out.print("Enter task due date (YYYY-MM-DD): ");
        String dueDate = scanner.nextLine();

        tasks.add(new Task(description, priority, dueDate));
        System.out.println("Task added successfully.");
    }

    private static void updateTask(Scanner scanner) {
        System.out.print("Enter task description to update: ");
        String description = scanner.nextLine();

        Task taskToUpdate = null;
        for (Task task : tasks) {
            if (task.description.equalsIgnoreCase(description)) {
                taskToUpdate = task;
                break;
            }
        }

        if (taskToUpdate != null) {
            System.out.print("Enter new priority (High, Medium, Low): ");
            taskToUpdate.priority = scanner.nextLine();

            System.out.print("Enter new due date (YYYY-MM-DD): ");
            taskToUpdate.dueDate = scanner.nextLine();

            System.out.println("Task updated successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void deleteTask(Scanner scanner) {
        System.out.print("Enter task description to delete: ");
        String description = scanner.nextLine();

        Task taskToDelete = null;
        for (Task task : tasks) {
            if (task.description.equalsIgnoreCase(description)) {
                taskToDelete = task;
                break;
            }
        }

        if (taskToDelete != null) {
            tasks.remove(taskToDelete);
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            System.out.println("\n--- All Tasks ---");
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private static void viewTasksByPriority(Scanner scanner) {
        System.out.print("Enter priority to filter by (High, Medium, Low): ");
        String priority = scanner.nextLine();

        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority().equalsIgnoreCase(priority)) {
                filteredTasks.add(task);
            }
        }

        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks found with the specified priority.");
        } else {
            System.out.println("\n--- Tasks with " + priority + " Priority ---");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }

    private static void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                String description = parts[0];
                String priority = parts[1].split(": ")[1];
                String dueDate = parts[2].split(": ")[1];
                tasks.add(new Task(description, priority, dueDate));
            }
        } catch (IOException e) {
            System.out.println("No previous tasks found.");
        }
    }

    private static void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.description + " | Priority: " + task.priority + " | Due: " + task.dueDate);
                writer.newLine();
            }
            System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }
}

