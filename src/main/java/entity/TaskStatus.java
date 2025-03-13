package entity;

import lombok.Getter;

@Getter
public enum TaskStatus {
    TODO(0),

    IN_PROGRESS(1),

    DONE(2);

    private final int code;

    TaskStatus(int code) {
        this.code = code;
    }

    public static TaskStatus parseStatus(String string) {
        for (var t : TaskStatus.values()) {
            if (string.equals(t.name().toLowerCase())) return t;
        }
        return null;
    }
}
