package repository;

import entity.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> tasks;

    public InMemoryTaskRepository() {
        tasks = new ArrayList<>();
    }

    public void delete(int id) {
        tasks.remove(id);
    }

    public void create(Task task) {
        tasks.add(task);
    }

    public List<Task> getAll() {
        return tasks;
    }

    public void update(Task task, int index) {
        tasks.set(index, task);
    }
}
