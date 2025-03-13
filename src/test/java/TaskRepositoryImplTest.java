import entity.Task;
import entity.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TaskRepositoryImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryImplTest {

    private TaskRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new TaskRepositoryImpl();
    }

    @Test
    void testGetAll() {
        List<Task> tasks = repository.getAll();
        assertEquals(3, tasks.size());
        assertEquals("task 1", tasks.get(0).getName());
        assertEquals("task 2", tasks.get(1).getName());
        assertEquals("task 3", tasks.get(2).getName());
    }

    @Test
    void testCreate() {
        Task newTask = new Task("task 4", "desc4", LocalDate.now(), TaskStatus.DONE);
        repository.create(newTask);
        List<Task> tasks = repository.getAll();
        assertEquals(4, tasks.size());
        assertEquals(newTask, tasks.get(3));
    }

    @Test
    void testDelete() {
        repository.delete(1);
        List<Task> tasks = repository.getAll();
        assertEquals(2, tasks.size());
        assertEquals("task 1", tasks.get(0).getName());
        assertEquals("task 3", tasks.get(1).getName());
    }

    @Test
    void testUpdate() {
        Task updatedTask = new Task("updated task", "updated desc", LocalDate.now(), TaskStatus.DONE);
        repository.update(updatedTask, 1);
        List<Task> tasks = repository.getAll();
        assertEquals(3, tasks.size());
        assertEquals(updatedTask, tasks.get(1));
    }

    @Test
    void testDeleteInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> repository.delete(10));
    }

    @Test
    void testUpdateInvalidIndex() {
        Task updatedTask = new Task("updated task", "updated desc", LocalDate.now(), TaskStatus.DONE);
        assertThrows(IndexOutOfBoundsException.class, () -> repository.update(updatedTask, 10));
    }
}