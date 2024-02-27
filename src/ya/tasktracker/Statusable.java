package ya.tasktracker;

public interface Statusable {
    void setStatus(TaskStatus status);
    TaskStatus getStatus();
}
