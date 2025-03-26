package controller;

import com.example.demo.controller.TaskController;
import com.example.demo.dto.RequestTaskDto;
import com.example.demo.dto.ResponseTaskDto;
import com.example.demo.entity.TaskStatus;
import com.example.demo.service.SortingOrder;
import com.example.demo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskController taskController;

    RequestTaskDto requestTaskDto;
    ResponseTaskDto responseTaskDto;

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
    }

    @Test
    void addTask_ShouldReturnCreatedResponse() {
        when(taskService.add(any(RequestTaskDto.class))).thenReturn(responseTaskDto);

        ResponseEntity<ResponseTaskDto> response = taskController.addTask(requestTaskDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseTaskDto, response.getBody());
        verify(taskService, times(1)).add(requestTaskDto);
    }

    @Test
    void showAllTasks_ShouldReturnOkResponseWithTasks() {
        List<ResponseTaskDto> tasks = Arrays.asList(responseTaskDto);
        when(taskService.getTasks()).thenReturn(tasks);

        ResponseEntity<List<ResponseTaskDto>> response = taskController.showAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
        verify(taskService, times(1)).getTasks();
    }

    @Test
    void deleteTask_ShouldReturnOkResponse() {
        Long taskId = 1L;
        doNothing().when(taskService).delete(taskId);

        ResponseEntity<String> response = taskController.deleteTask(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(taskService, times(1)).delete(taskId);
    }

    @Test
    void updateTask_ShouldReturnOkResponseWithUpdatedTask() {
        Long taskId = 1L;
        when(taskService.update(any(Long.class), any(RequestTaskDto.class))).thenReturn(responseTaskDto);

        ResponseEntity<ResponseTaskDto> response = taskController.updateTask(taskId, requestTaskDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseTaskDto, response.getBody());
        verify(taskService, times(1)).update(taskId, requestTaskDto);
    }

    @Test
    void filterByStatus_ShouldReturnFilteredTasks() {
        TaskStatus status = TaskStatus.DONE;
        List<ResponseTaskDto> filteredTasks = Arrays.asList(responseTaskDto);
        when(taskService.filter(status)).thenReturn(filteredTasks);

        ResponseEntity<List<ResponseTaskDto>> response = taskController.filter(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filteredTasks, response.getBody());
        verify(taskService, times(1)).filter(status);
    }

    @Test
    void sortByDeadline_ShouldReturnSortedTasks() {
        SortingOrder order = SortingOrder.ASC;
        List<ResponseTaskDto> sortedTasks = Arrays.asList(responseTaskDto);
        when(taskService.sortByDeadline(order)).thenReturn(sortedTasks);

        ResponseEntity<List<ResponseTaskDto>> response = taskController.sortByDeadline(order);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sortedTasks, response.getBody());
        verify(taskService, times(1)).sortByDeadline(order);
    }

    @Test
    void sortByStatus_ShouldReturnSortedTasks() {
        SortingOrder order = SortingOrder.ASC;
        List<ResponseTaskDto> sortedTasks = Arrays.asList(responseTaskDto);
        when(taskService.sortByStatus(order)).thenReturn(sortedTasks);

        ResponseEntity<List<ResponseTaskDto>> response = taskController.sortByStatus(order);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sortedTasks, response.getBody());
        verify(taskService, times(1)).sortByStatus(order);
    }

    @Test
    void sortByDeadline_ShouldUseDefaultOrderWhenNotProvided() {
        List<ResponseTaskDto> sortedTasks = Arrays.asList(responseTaskDto);
        when(taskService.sortByDeadline(SortingOrder.ASC)).thenReturn(sortedTasks);

        ResponseEntity<List<ResponseTaskDto>> response = taskController.sortByDeadline(SortingOrder.ASC);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sortedTasks, response.getBody());
        verify(taskService, times(1)).sortByDeadline(SortingOrder.ASC);
    }

    @Test
    void sortByStatus_ShouldUseDefaultOrderWhenNotProvided() {
        List<ResponseTaskDto> sortedTasks = Arrays.asList(responseTaskDto);
        when(taskService.sortByStatus(SortingOrder.ASC)).thenReturn(sortedTasks);

        ResponseEntity<List<ResponseTaskDto>> response = taskController.sortByStatus(SortingOrder.ASC);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sortedTasks, response.getBody());
        verify(taskService, times(1)).sortByStatus(SortingOrder.ASC);
    }
}
