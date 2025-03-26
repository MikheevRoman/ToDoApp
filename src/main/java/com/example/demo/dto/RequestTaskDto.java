package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.demo.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestTaskDto {

    @JsonProperty
    String name;

    @JsonProperty
    String description;

    @JsonProperty
    LocalDate deadline;

    @JsonProperty
    TaskStatus status;

}
