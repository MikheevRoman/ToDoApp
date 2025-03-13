package controller;

import entity.Task;
import lombok.AllArgsConstructor;
import service.TaskService;

import java.util.List;

@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    public void addTask(Task task) {
        taskService.add(task);
    }

    public void updateTask(Task task, int index) {
        taskService.update(task, index);
    }

    public void delete(int index) {
        taskService.delete(index);
    }

    public List<Task> getAll() {
        return taskService.getTasks();
    }
}
