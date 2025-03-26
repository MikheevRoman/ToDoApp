package com.example.demo.controller;

import com.example.demo.dto.RequestTaskDto;
import com.example.demo.dto.ResponseTaskDto;
import com.example.demo.entity.TaskStatus;
import com.example.demo.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.SortingOrder;
import com.example.demo.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ResponseTaskDto> addTask(@RequestBody RequestTaskDto requestTaskDto) {
        System.out.println(requestTaskDto.toString());
        return new ResponseEntity<>(taskService.add(requestTaskDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTaskDto>> showAllTasks() {
        return new ResponseEntity<>(taskService.getTasks(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "id") Long id) {
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTaskDto> updateTask(
            @PathVariable(name = "id") Long id,
            @RequestBody RequestTaskDto requestTaskDto
    ) {
        return new ResponseEntity<>(taskService.update(id, requestTaskDto), HttpStatus.OK);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<ResponseTaskDto>> filter(@PathVariable(name = "status") TaskStatus taskStatus) {
        return new ResponseEntity<>(taskService.filter(taskStatus), HttpStatus.OK);
    }

    @GetMapping("/sortByDeadline")
    public ResponseEntity<List<ResponseTaskDto>> sortByDeadline(
            @RequestParam(name = "order", defaultValue = "ASC") SortingOrder order
    ) {
        return new ResponseEntity<>(taskService.sortByDeadline(order), HttpStatus.OK);
    }

    @GetMapping("/sortByStatus")
    public ResponseEntity<List<ResponseTaskDto>> sortByStatus(
            @RequestParam(name = "order", defaultValue = "ASC") SortingOrder order
    ) {
        return new ResponseEntity<>(taskService.sortByStatus(order), HttpStatus.OK);
    }
}
