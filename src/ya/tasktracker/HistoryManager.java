package ya.tasktracker;

import java.util.ArrayList;

public interface HistoryManager {
    void add(AbstractTask task);
    ArrayList<AbstractTask>getHistory();
}
