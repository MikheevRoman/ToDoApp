package repository;

import entity.Task;

import java.util.List;

public interface TaskRepository {
    void delete(int id);

    void create(Task task);

    List<Task> getAll();

    void update(Task task, int index);
}
