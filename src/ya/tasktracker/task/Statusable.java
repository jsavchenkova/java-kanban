package ya.tasktracker.task;

public interface Statusable {
    void setStatus(TaskStatus status);
    TaskStatus getStatus();
}
