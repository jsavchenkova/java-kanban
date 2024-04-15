package ya.tasktracker.task;

import ya.tasktracker.constants.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {

    private TaskStatus status;
    private int id;
    private String name;
    private String description;
    private Duration duration;
    private LocalDateTime startTime;

    private DateTimeFormatter formatter;

    public Task() {
        formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    }

    public Task(String name) {
        formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        this.name = name;
        status = TaskStatus.NEW;
    }

    public Task(String[] params) {
        formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        id = Integer.parseInt(params[0]);
        name = params[2];
        status = TaskStatus.valueOf(params[3]);
        description = params[4];
        if (!params[6].isBlank()) {
            startTime = LocalDateTime.parse(params[6], formatter);
        }
        if (!params[7].isBlank()) {
            duration = Duration.ofSeconds(Long.parseLong(params[8]));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        if (description == null) return "";
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusSeconds(duration.getSeconds());
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + status +
                '}';
    }


    public TaskStatus getStatus() {
        return status;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(getName(), task.getName()) && Objects.equals(getDescription(), task.getDescription())
                && status == task.status;
    }


    public int hashCode() {
        return Objects.hash(getName(), getDescription(), status);
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String serializeToString() {
        String start = "";
        String finish = "";
        String duration = "";
        if (getStartTime() != null && getDuration() != null) {
            start = getStartTime().format(formatter);
            finish = getEndTime().format(formatter);
            duration = String.valueOf(getDuration().getSeconds());
        }

        return String.format("%s,%s,%s,%s,%s,,%s,%s,%s", getId(), TaskType.TASK, getName(), getStatus(), getDescription(),
                start, finish, duration);
    }

    Task fromString(String value) {
        String[] vals = value.split(",");
        return new Task(vals);
    }

}
