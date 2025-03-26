package service;

import com.example.demo.dto.RequestTaskDto;
import com.example.demo.dto.ResponseTaskDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskStatus;
import com.example.demo.exception.IllegalSortingOrderException;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.SortingOrder;
import com.example.demo.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private RequestTaskDto requestTaskDto;
    private ResponseTaskDto responseTaskDto;
    private Task task;

    @BeforeEach
    void setUp() {
        LocalDate testDeadline = LocalDate.now().plusDays(7);

        requestTaskDto = new RequestTaskDto(
                "Test Task",
                "Test Description",
                testDeadline,
                TaskStatus.TODO
        );

        responseTaskDto = new ResponseTaskDto(
                1L,
                "Test Task",
                "Test Description",
                testDeadline,
                TaskStatus.TODO
        );

        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setDeadline(testDeadline);
        task.setStatus(TaskStatus.TODO);
    }

    @Test
    void add_ShouldSaveTaskAndReturnResponseDto() {
        when(taskMapper.toTask(requestTaskDto)).thenReturn(task);
        when(repository.save(task)).thenReturn(task);
        when(taskMapper.toResponseTaskDto(task)).thenReturn(responseTaskDto);

        ResponseTaskDto result = taskService.add(requestTaskDto);

        assertNotNull(result);
        assertEquals(responseTaskDto, result);
        verify(taskMapper).toTask(requestTaskDto);
        verify(repository).save(task);
        verify(taskMapper).toResponseTaskDto(task);
    }

    @Test
    void getTasks_ShouldReturnListOfResponseTaskDto() {
        List<Task> tasks = List.of(task);
        when(repository.findAll()).thenReturn(tasks);
        when(taskMapper.toResponseTaskDto(task)).thenReturn(responseTaskDto);

        List<ResponseTaskDto> result = taskService.getTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseTaskDto, result.getFirst());
        verify(repository).findAll();
        verify(taskMapper).toResponseTaskDto(task);
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        Long taskId = 1L;
        doNothing().when(repository).deleteById(taskId);

        taskService.delete(taskId);

        verify(repository).deleteById(taskId);
    }

    @Test
    void filter_ShouldReturnFilteredTasksByStatus() {
        TaskStatus status = TaskStatus.TODO;
        List<Task> tasks = List.of(task);
        when(repository.findByStatus(status)).thenReturn(tasks);
        when(taskMapper.toResponseTaskDto(task)).thenReturn(responseTaskDto);

        List<ResponseTaskDto> result = taskService.filter(status);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseTaskDto, result.getFirst());
        verify(repository).findByStatus(status);
        verify(taskMapper).toResponseTaskDto(task);
    }

    @Test
    void sortByStatus_ShouldSortAscending() {
        when(repository.findAllByOrderByStatusAsc()).thenReturn(List.of(task));
        when(taskMapper.toResponseTaskDto(task)).thenReturn(responseTaskDto);

        List<ResponseTaskDto> result = taskService.sortByStatus(SortingOrder.ASC);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseTaskDto, result.getFirst());
        verify(repository).findAllByOrderByStatusAsc();
        verify(taskMapper).toResponseTaskDto(task);
    }

    @Test
    void sortByStatus_ShouldSortDescending() {
        when(repository.findAllByOrderByStatusDesc()).thenReturn(List.of(task));
        when(taskMapper.toResponseTaskDto(task)).thenReturn(responseTaskDto);

        List<ResponseTaskDto> result = taskService.sortByStatus(SortingOrder.DESC);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseTaskDto, result.getFirst());
        verify(repository).findAllByOrderByStatusDesc();
        verify(taskMapper).toResponseTaskDto(task);
    }

    @Test
    void sortByStatus_ShouldThrowExceptionForInvalidOrder() {
        assertThrows(IllegalSortingOrderException.class, () -> taskService.sortByStatus(null));
    }

    @Test
    void sortByDeadline_ShouldSortAscending() {
        when(repository.findAllByOrderByDeadlineAsc()).thenReturn(List.of(task));
        when(taskMapper.toResponseTaskDto(task)).thenReturn(responseTaskDto);

        List<ResponseTaskDto> result = taskService.sortByDeadline(SortingOrder.ASC);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseTaskDto, result.getFirst());
        verify(repository).findAllByOrderByDeadlineAsc();
        verify(taskMapper).toResponseTaskDto(task);
    }

    @Test
    void sortByDeadline_ShouldSortDescending() {
        when(repository.findAllByOrderByDeadlineDesc()).thenReturn(List.of(task));
        when(taskMapper.toResponseTaskDto(task)).thenReturn(responseTaskDto);

        List<ResponseTaskDto> result = taskService.sortByDeadline(SortingOrder.DESC);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseTaskDto, result.getFirst());
        verify(repository).findAllByOrderByDeadlineDesc();
        verify(taskMapper).toResponseTaskDto(task);
    }

    @Test
    void sortByDeadline_ShouldThrowExceptionForInvalidOrder() {
        assertThrows(IllegalSortingOrderException.class, () -> taskService.sortByDeadline(null));
    }
}