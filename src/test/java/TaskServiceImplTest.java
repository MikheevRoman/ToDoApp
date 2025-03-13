import entity.Task;
import entity.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.TaskRepository;
import service.TaskServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTask() {
        Task task = new Task("Task 1", "Description 1", LocalDate.now(), TaskStatus.TODO);
        taskService.add(task);
        verify(repository, times(1)).create(task);
    }

    @Test
    void testGetTasks() {
        List<Task> tasks = Arrays.asList(
                new Task("Task 1", "Description 1", LocalDate.now(), TaskStatus.TODO),
                new Task("Task 2", "Description 2", LocalDate.now(), TaskStatus.TODO)
        );
        when(repository.getAll()).thenReturn(tasks);
        List<Task> result = taskService.getTasks();
        assertEquals(2, result.size());
        verify(repository, times(1)).getAll();
    }

    @Test
    void testUpdateTask() {
        Task task = new Task("Updated Task", "Updated Description", LocalDate.now(), TaskStatus.TODO);
        int index = 0;
        taskService.update(task, index);
        verify(repository, times(1)).update(task, index);
    }

    @Test
    void testDeleteTask() {
        int index = 0;
        taskService.delete(index);
        verify(repository, times(1)).delete(index);
    }
}