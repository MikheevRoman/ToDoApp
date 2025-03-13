package repository;

import entity.Task;
import entity.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {

    private final List<Task> tasks;

    public TaskRepositoryImpl() {
        tasks = new ArrayList<>();
        tasks.add(new Task("task 1", "desc1", LocalDate.now(), TaskStatus.TODO));
        tasks.add(new Task("task 2", "desc2", LocalDate.now().minusDays(1L), TaskStatus.IN_PROGRESS));
        tasks.add(new Task("task 3", "desc3", LocalDate.now().minusDays(32L), TaskStatus.TODO));
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
