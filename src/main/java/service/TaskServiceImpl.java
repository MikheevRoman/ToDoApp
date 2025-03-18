package service;

import entity.Task;
import entity.TaskStatus;
import lombok.RequiredArgsConstructor;
import repository.TaskRepository;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
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

    public List<Task> filter(TaskStatus taskStatus) {
        return repository.getAll().stream()
                .filter(task -> task.getStatus() == taskStatus)
                .toList();
    }

    public List<Task> sortByStatus(SortingOrder sortingOrder) {
        var tasks = repository.getAll();
        if (sortingOrder == SortingOrder.ASC) {
            tasks = tasks.stream()
                    .sorted(Comparator.comparing(Task::getStatus))
                    .toList();
        } else if (sortingOrder == SortingOrder.DESC) {
            tasks = tasks.stream()
                    .sorted(Comparator.comparing(Task::getStatus).reversed())
                    .toList();
        }
        return tasks;
    }
}
