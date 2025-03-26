package com.example.demo.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.dto.RequestTaskDto;
import com.example.demo.dto.ResponseTaskDto;
import com.example.demo.entity.Task;
import com.example.demo.exception.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public TaskMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseTaskDto toResponseTaskDto(Task task) {
        try {
            if (task == null) throw new MappingException("null object");
            return objectMapper.convertValue(task, ResponseTaskDto.class);
        } catch (IllegalArgumentException e) {
            throw new MappingException("illegal parameter");
        }
    }

    public Task toTask(RequestTaskDto requestTaskDto) {
        try {
            if (requestTaskDto == null) throw new MappingException("null object");
            return objectMapper.convertValue(requestTaskDto, Task.class);
        } catch (IllegalArgumentException e) {
            throw new MappingException("illegal parameter");
        }
    }

    public void updateFromDto(RequestTaskDto dto, Task entity) {
        Optional.ofNullable(dto.getName()).ifPresent(entity::setName);
        Optional.ofNullable(dto.getDescription()).ifPresent(entity::setDescription);
        Optional.ofNullable(dto.getDeadline()).ifPresent(entity::setDeadline);
        Optional.ofNullable(dto.getStatus()).ifPresent(entity::setStatus);
    }

}
