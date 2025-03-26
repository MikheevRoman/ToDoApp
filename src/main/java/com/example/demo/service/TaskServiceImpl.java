package com.example.demo.service;

import com.example.demo.dto.RequestTaskDto;
import com.example.demo.dto.ResponseTaskDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskStatus;
import com.example.demo.exception.IllegalSortingOrderException;
import com.example.demo.mapper.TaskMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.repository.TaskRepository;

import java.util.List;

import static com.example.demo.service.SortingOrder.ASC;
import static com.example.demo.service.SortingOrder.DESC;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.repository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional
    public ResponseTaskDto add(RequestTaskDto requestTaskDto) {
        Task task = taskMapper.toTask(requestTaskDto);
        return taskMapper.toResponseTaskDto(repository.save(task));
    }

    @Override
    @Transactional
    public List<ResponseTaskDto> getTasks() {
        return repository.findAll().stream()
                .map(taskMapper::toResponseTaskDto)
                .toList();
    }

    @Override
    @Transactional
    public ResponseTaskDto update(Long id, RequestTaskDto requestTaskDto) {
        Task task = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        taskMapper.updateFromDto(requestTaskDto, task);
        return taskMapper.toResponseTaskDto(task);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<ResponseTaskDto> filter(TaskStatus taskStatus) {
        return repository.findByStatus(taskStatus).stream()
                .map(taskMapper::toResponseTaskDto)
                .toList();
    }

    @Override
    @Transactional
    public List<ResponseTaskDto> sortByStatus(SortingOrder sortingOrder) {
        if (sortingOrder == ASC) {
            return repository.findAllByOrderByStatusAsc().stream()
                    .map(taskMapper::toResponseTaskDto)
                    .toList();
        } else if (sortingOrder == DESC) {
            return repository.findAllByOrderByStatusDesc().stream()
                    .map(taskMapper::toResponseTaskDto)
                    .toList();
        } else {
            throw new IllegalSortingOrderException("incorrect sorting order");
        }
    }

    @Override
    @Transactional
    public List<ResponseTaskDto> sortByDeadline(SortingOrder sortingOrder) {
        if (sortingOrder == ASC) {
            return repository.findAllByOrderByDeadlineAsc().stream()
                    .map(taskMapper::toResponseTaskDto)
                    .toList();
        } else if (sortingOrder == DESC) {
            return repository.findAllByOrderByDeadlineDesc().stream()
                    .map(taskMapper::toResponseTaskDto)
                    .toList();
        } else {
            throw new IllegalSortingOrderException("incorrect sorting order");
        }
    }
}
