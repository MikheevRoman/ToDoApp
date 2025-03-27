package handler;

import com.example.demo.controller.TaskController;
import com.example.demo.exception.MappingException;
import com.example.demo.handler.GlobalExceptionHandler;
import com.example.demo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskController taskController;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void whenEntityNotFound_thenReturnsNotFound() throws Exception {
        when(taskService.update(any(), any()))
                .thenThrow(new EntityNotFoundException("Not found"));

        mockMvc.perform(patch("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenIncorrectMapping_thenThrowsMappingException() throws Exception {
        when(taskService.add(any())).thenThrow(MappingException.class);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

}
