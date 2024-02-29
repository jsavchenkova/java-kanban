package ya.tasktracker.task;

public interface ITask {
    int getId();
    void setId(int id);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
}
