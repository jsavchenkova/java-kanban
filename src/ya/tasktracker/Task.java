package ya.tasktracker;

import java.util.Objects;

public class Task extends AbstractTask implements Statusable{

    private TaskStatus status;

    public Task(String name){
       super(name);
        status = TaskStatus.NEW;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(getName(), task.getName()) && Objects.equals(getDescription(), task.getDescription())
                && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), status);
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }


}
