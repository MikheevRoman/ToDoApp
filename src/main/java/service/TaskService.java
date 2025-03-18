package service;

import entity.Task;
import entity.TaskStatus;

import java.util.List;

public interface TaskService {

    void add(Task task);

    List<Task> getTasks();

    void update(Task task, int index);

    void delete(int index);

    List<Task> filter(TaskStatus taskStatus);

    List<Task> sortByStatus(SortingOrder sortingOrder);
}
