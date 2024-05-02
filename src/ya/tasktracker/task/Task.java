package ya.tasktracker.task;

import com.google.gson.annotations.Expose;
import jdk.jfr.Experimental;
import ya.tasktracker.constants.Formatters;
import ya.tasktracker.constants.TaskStatus;
import ya.tasktracker.constants.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {

    @Expose
    private TaskStatus status;
    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private Duration duration;
    @Expose
    private ZonedDateTime startTime;

    private DateTimeFormatter formatter;
    private String dateTimeFormat = Formatters.dateTimeFormat;

    public Task() {
        formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    public Task(String name) {
        formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        this.name = name;
        status = TaskStatus.NEW;
    }

    public Task(String[] params) {
        formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        id = Integer.parseInt(params[0]);
        name = params[2];
        status = TaskStatus.valueOf(params[3]);
        description = params[4];
        if (!params[6].isBlank()) {
            startTime = ZonedDateTime.parse(params[6], formatter);
        }
        if (!params[7].isBlank()) {
            duration = Duration.ofMinutes(Long.parseLong(params[8]));
        }
    }

    public Integer getId() {
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

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
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
        String durationTask = "";
        if (getStartTime() != null && getDuration() != null) {
            start = getStartTime().format(formatter);
            finish = getEndTime().format(formatter);
            durationTask = String.valueOf(getDuration().toMinutes());
        }

        return String.format("%s,%s,%s,%s,%s,,%s,%s,%s", getId(), TaskType.TASK, getName(), getStatus(), getDescription(),
                start, finish, durationTask);
    }

    Task fromString(String value) {
        String[] vals = value.split(",");
        return new Task(vals);
    }

}
