package view;

import controller.TaskController;
import entity.Task;
import entity.TaskStatus;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class ConsoleView {
    private final Scanner scanner;
    private final TaskController taskController;

    public void start() {
        while (true) {
            System.out.print("""
                TODO APP
                
                Whats next?
                add – добавить задачу.
                list – вывести список задач.
                edit – редактировать задачу.
                delete – удалить задачу.
                filter – отфильтровать задачи по статусу.
                sort – отсортировать задачи.
                exit – выход из системы.
                
                Command:\s""");

            switch (scanner.nextLine()) {
                case "exit" -> System.exit(0);
                case "add" -> addTask();
                case "list" -> showAllTasks();
                case "delete" -> delete();
                case "edit" -> editTask();
                case "filter" -> filter();
                case "sort" -> sort();
                default -> System.out.println("Unknown command");
            }
        }
    }

    private void addTask() {
        System.out.println("New task");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Description: ");
        String desc = scanner.nextLine();

        System.out.print("Deadline (dd.mm.yyyy): ");
        LocalDate deadline = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        taskController.addTask(new Task(name, desc, deadline, TaskStatus.TODO));
    }

    private void showAllTasks() {
        System.out.println("TASKS");
        printTaskTable(taskController.getAll());
    }

    private void delete() {
        System.out.print("Number of the element to be removed: ");
        taskController.delete(scanner.nextInt() - 1);
    }

    private void editTask() {
        System.out.print("Number of the element to be edited: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        System.out.println("Edit the element");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Description: ");
        String desc = scanner.nextLine();

        System.out.print("Deadline (dd.mm.yyyy): ");
        LocalDate deadline = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        System.out.print("Status: ");
        TaskStatus status = TaskStatus.parseStatus(scanner.nextLine());

        taskController.updateTask(new Task(name, desc, deadline, status), index);
    }

    private void filter() {
        System.out.print("Status applied in the filter: ");
        TaskStatus status = TaskStatus.parseStatus(scanner.nextLine());

        var tasks = taskController.getAll().stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
        printTaskTable(tasks);
    }

    private void printTaskTable(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String str = String.format(
                    "%d\t| %s | %s | %s | %s\n",
                    i + 1,
                    task.getStatus().toString(),
                    task.getName(),
                    task.getDescription(),
                    task.getDeadline().toString()
            );
            System.out.println(str);
        }
    }

    private void sort() {
        System.out.print("By what parameter to sort data [data, status]: ");
        String mode = scanner.nextLine();

        System.out.print("What order [asc, desc]: ");
        String order = scanner.nextLine();

        var tasks = taskController.getAll();
        if (mode.equals("status")) {
            if (order.equals("asc")) {
                tasks = tasks.stream()
                        .sorted(Task::compare)
                        .toList();
            } else if (order.equals("desc")) {
                tasks = tasks.stream()
                        .sorted(Task::compareReverseOrder)
                        .toList();
            }
        } else if (mode.equals("data")) {
            if (order.equals("asc")) {
                tasks = tasks.stream()
                        .sorted(Comparator.comparing(Task::getDeadline))
                        .toList();
            } else if (order.equals("desc")) {
                tasks = tasks.stream()
                        .sorted(Comparator.comparing(Task::getDeadline).reversed())
                        .toList();
            }
        }
        printTaskTable(tasks);
    }
}
