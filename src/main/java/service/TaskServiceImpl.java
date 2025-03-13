package service;

import entity.Task;
import lombok.AllArgsConstructor;
import repository.TaskRepository;

import java.util.List;

@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Override
    public void add(Task task) {
        repository.create(task);
    }

    @Override
    public List<Task> getTasks() {
        return repository.getAll();
    }

    @Override
    public void update(Task task, int index) {
        repository.update(task, index);
    }

    @Override
    public void delete(int index) {
        repository.delete(index);
    }
}
