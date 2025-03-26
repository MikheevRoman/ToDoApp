package mapper;

import com.example.demo.dto.RequestTaskDto;
import com.example.demo.dto.ResponseTaskDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskStatus;
import com.example.demo.exception.MappingException;
import com.example.demo.mapper.TaskMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskMapperTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TaskMapper taskMapper;

    private RequestTaskDto requestTaskDto;
    private Task task;
    private ResponseTaskDto responseTaskDto;

    @BeforeEach
    void setUp() {
        LocalDate testDeadline = LocalDate.now().plusDays(7);

        requestTaskDto = new RequestTaskDto(
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

        responseTaskDto = new ResponseTaskDto(
                1L,
                "Test Task",
                "Test Description",
                testDeadline,
                TaskStatus.TODO
        );
    }

    @Test
    void toResponseTaskDto_ShouldMapTaskToResponseDto() {
        when(objectMapper.convertValue(task, ResponseTaskDto.class)).thenReturn(responseTaskDto);

        ResponseTaskDto result = taskMapper.toResponseTaskDto(task);

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getName(), result.getName());
        assertEquals(task.getDescription(), result.getDescription());
        assertEquals(task.getDeadline(), result.getDeadline());
        assertEquals(task.getStatus(), result.getStatus());
        verify(objectMapper).convertValue(task, ResponseTaskDto.class);
    }

    @Test
    void toResponseTaskDto_ShouldThrowMappingExceptionWhenConversionFails() {
        when(objectMapper.convertValue(any(), eq(ResponseTaskDto.class)))
                .thenThrow(new IllegalArgumentException("Conversion error"));

        assertThrows(MappingException.class, () -> taskMapper.toResponseTaskDto(task));
    }

    @Test
    void toTask_ShouldMapCreateTaskDtoToTask() {
        when(objectMapper.convertValue(requestTaskDto, Task.class)).thenReturn(task);

        Task result = taskMapper.toTask(requestTaskDto);

        assertNotNull(result);
        assertEquals(requestTaskDto.getName(), result.getName());
        assertEquals(requestTaskDto.getDescription(), result.getDescription());
        assertEquals(requestTaskDto.getDeadline(), result.getDeadline());
        assertEquals(requestTaskDto.getStatus(), result.getStatus());
        verify(objectMapper).convertValue(requestTaskDto, Task.class);
    }

    @Test
    void toTask_ShouldThrowMappingExceptionWhenConversionFails() {
        when(objectMapper.convertValue(any(), eq(Task.class)))
                .thenThrow(new IllegalArgumentException("Conversion error"));

        assertThrows(MappingException.class, () -> taskMapper.toTask(requestTaskDto));
    }

    @Test
    void toResponseTaskDto_ShouldHandleNullInput() {
        assertThrows(MappingException.class, () -> taskMapper.toResponseTaskDto(null));
    }

    @Test
    void toTask_ShouldHandleNullInput() {
        assertThrows(MappingException.class, () -> taskMapper.toTask(null));
    }

    @Test
    void toResponseTaskDto_ShouldMapAllFieldsCorrectly() {
        Task complexTask = new Task();
        complexTask.setId(2L);
        complexTask.setName("Complex Task");
        complexTask.setDescription("Detailed Description");
        complexTask.setDeadline(LocalDate.now().plusDays(14));
        complexTask.setStatus(TaskStatus.IN_PROGRESS);

        ResponseTaskDto expectedDto = new ResponseTaskDto(
                2L,
                "Complex Task",
                "Detailed Description",
                LocalDate.now().plusDays(14),
                TaskStatus.IN_PROGRESS
        );

        when(objectMapper.convertValue(complexTask, ResponseTaskDto.class)).thenReturn(expectedDto);

        ResponseTaskDto result = taskMapper.toResponseTaskDto(complexTask);

        assertNotNull(result);
        assertEquals(complexTask.getId(), result.getId());
        assertEquals(complexTask.getName(), result.getName());
        assertEquals(complexTask.getDescription(), result.getDescription());
        assertEquals(complexTask.getDeadline(), result.getDeadline());
        assertEquals(complexTask.getStatus(), result.getStatus());
    }

    @Test
    void toTask_ShouldMapAllFieldsFromCreateDto() {
        RequestTaskDto complexDto = new RequestTaskDto(
                "New Task",
                "New Description",
                LocalDate.now().plusDays(10),
                TaskStatus.DONE
        );

        Task expectedTask = new Task();
        expectedTask.setName("New Task");
        expectedTask.setDescription("New Description");
        expectedTask.setDeadline(LocalDate.now().plusDays(10));
        expectedTask.setStatus(TaskStatus.DONE);

        when(objectMapper.convertValue(complexDto, Task.class)).thenReturn(expectedTask);

        Task result = taskMapper.toTask(complexDto);

        assertNotNull(result);
        assertEquals(complexDto.getName(), result.getName());
        assertEquals(complexDto.getDescription(), result.getDescription());
        assertEquals(complexDto.getDeadline(), result.getDeadline());
        assertEquals(complexDto.getStatus(), result.getStatus());
    }
}