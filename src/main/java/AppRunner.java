import controller.TaskController;
import repository.TaskRepository;
import repository.InMemoryTaskRepository;
import service.TaskService;
import service.TaskServiceImpl;

import java.util.Scanner;

public class AppRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskRepository taskRepository = new InMemoryTaskRepository();
        TaskService taskService = new TaskServiceImpl(taskRepository);
        TaskController taskController = new TaskController(scanner, taskService);
        
        taskController.start();
    }
}
