package service;

import entity.Task;

import java.util.List;

public interface TaskService {
    void add(Task task);

    List<Task> getTasks();

    void update(Task task, int index);

    void delete(int index);
}
