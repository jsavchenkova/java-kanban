package ya.tasktracker.manager;

import ya.tasktracker.task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    void remove(int id);

    List<Task> getHistory();
}
