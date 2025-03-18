package controller;

import entity.Task;
import entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import service.SortingOrder;
import service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Data
@AllArgsConstructor
public class TaskController {

    private final Scanner scanner;

    private final TaskService taskService;

    private static final String MAIN_LABEL = """
                TODO APP
                
                Whats next?
                add – добавить задачу.
                list – вывести список задач.
                edit – редактировать задачу.
                delete – удалить задачу.
                filter – отфильтровать задачи по статусу.
                sort – отсортировать задачи по дате.
                exit – выход из системы.
                
                Command:\s""";

    public void start() {
        while (true) {
            System.out.print(MAIN_LABEL);
            try {
                Command command = Command.valueOf(scanner.nextLine().toUpperCase());

                switch (command) {
                    case EXIT -> System.exit(0);
                    case ADD -> addTask();
                    case LIST -> showAllTasks();
                    case DELETE -> delete();
                    case EDIT -> editTask();
                    case FILTER -> filter();
                    case SORT -> sort();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect command, try again");
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

        taskService.add(new Task(name, desc, deadline, TaskStatus.TODO));
    }

    private void showAllTasks() {
        System.out.println("TASKS");
        printTaskTable(taskService.getTasks());
    }

    private void delete() {
        System.out.print("Number of the element to be removed: ");
        taskService.delete(scanner.nextInt() - 1);
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
        TaskStatus status = TaskStatus.valueOf(scanner.nextLine().toUpperCase());

        taskService.update(new Task(name, desc, deadline, status), index);
    }

    private void filter() {
        System.out.printf("Status applied in the filter %s: ", Arrays.toString(TaskStatus.values()));
        TaskStatus status = TaskStatus.valueOf(scanner.nextLine().toUpperCase());

        var tasks = taskService.filter(status);
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
        System.out.printf("What order %s: ", Arrays.toString(SortingOrder.values()));
        SortingOrder sortingOrder = SortingOrder.valueOf(scanner.nextLine().toUpperCase());

        var tasks = taskService.sortByStatus(sortingOrder);
        printTaskTable(tasks);
    }
}
