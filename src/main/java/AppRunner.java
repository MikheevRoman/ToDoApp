import controller.TaskController;
import repository.TaskRepository;
import repository.TaskRepositoryImpl;
import service.TaskService;
import service.TaskServiceImpl;
import view.ConsoleView;

import java.util.Scanner;

public class AppRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskRepository taskRepository = new TaskRepositoryImpl();
        TaskService taskService = new TaskServiceImpl(taskRepository);
        TaskController taskController = new TaskController(taskService);
        ConsoleView consoleView = new ConsoleView(scanner, taskController);

        consoleView.start();
    }
}
