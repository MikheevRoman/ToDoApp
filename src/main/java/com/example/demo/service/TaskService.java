package com.example.demo.service;

import com.example.demo.dto.RequestTaskDto;
import com.example.demo.dto.ResponseTaskDto;
import com.example.demo.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    ResponseTaskDto add(RequestTaskDto task);

    List<ResponseTaskDto> getTasks();

    ResponseTaskDto update(Long id, RequestTaskDto task);

    void delete(Long id);

    List<ResponseTaskDto> filter(TaskStatus taskStatus);

    List<ResponseTaskDto> sortByStatus(SortingOrder sortingOrder);

    List<ResponseTaskDto> sortByDeadline(SortingOrder sortingOrder);
}
