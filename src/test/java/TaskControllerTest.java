import controller.TaskController;
import entity.Task;
import entity.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.TaskService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTask() {
        Task task = new Task("Task 1", "Description 1", LocalDate.now(), TaskStatus.DONE);
        taskController.addTask(task);
        verify(taskService, times(1)).add(task);
    }

    @Test
    void testUpdateTask() {
        Task task = new Task("Updated Task", "Updated Description", LocalDate.now(), TaskStatus.DONE);
        int index = 0;
        taskController.updateTask(task, index);
        verify(taskService, times(1)).update(task, index);
    }

    @Test
    void testDeleteTask() {
        int index = 0;
        taskController.delete(index);
        verify(taskService, times(1)).delete(index);
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(
                new Task("Task 1", "Description 1", LocalDate.now(), TaskStatus.DONE),
                new Task("Task 2", "Description 2", LocalDate.now(), TaskStatus.DONE)
        );
        when(taskService.getTasks()).thenReturn(tasks);
        List<Task> result = taskController.getAll();
        assertEquals(2, result.size());
        verify(taskService, times(1)).getTasks();
    }
}