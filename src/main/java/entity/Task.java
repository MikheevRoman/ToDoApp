package entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    final String name;
    final String description;
    final LocalDate deadline;
    final TaskStatus status;


    public static int compare(Task t1, Task t2) {
        return t1.getStatus().getCode() - t2.getStatus().getCode();
    }

    public static int compareReverseOrder(Task t1, Task t2) {
        return t2.getStatus().getCode() - t1.getStatus().getCode();
    }
}
