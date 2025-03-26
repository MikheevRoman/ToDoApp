package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus taskStatus);

    List<Task> findAllByOrderByDeadlineAsc();

    List<Task> findAllByOrderByDeadlineDesc();

    List<Task> findAllByOrderByStatusAsc();

    List<Task> findAllByOrderByStatusDesc();

}
