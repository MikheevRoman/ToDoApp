package entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

    final String name;

    final String description;

    final LocalDate deadline;

    final TaskStatus status;

}
