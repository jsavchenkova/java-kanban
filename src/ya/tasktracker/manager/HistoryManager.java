package ya.tasktracker.manager;

import ya.tasktracker.task.AbstractTask;
import ya.tasktracker.task.ITask;
import ya.tasktracker.task.IndexTask;

import java.util.ArrayList;

public interface HistoryManager {
    void add(ITask task);
    ArrayList<ITask>getHistory();
}
