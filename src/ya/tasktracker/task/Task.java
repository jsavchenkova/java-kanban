package ya.tasktracker.task;

import java.util.Objects;

public class Task {

    private TaskStatus status;
    private int id;
    private String name;
    private String description;

    public Task(String name) {
        this.name = name;
        status = TaskStatus.NEW;
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


}
