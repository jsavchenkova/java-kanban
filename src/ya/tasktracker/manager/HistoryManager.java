package ya.tasktracker.manager;

import ya.tasktracker.task.AbstractTask;
import ya.tasktracker.task.ITask;
import ya.tasktracker.task.IndexTask;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {
    void add(ITask task);
    List<ITask> getHistory();
}
