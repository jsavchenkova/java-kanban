package ya.tasktracker.manager;

import java.util.List;

public interface HistoryManager {
    void add(int task);

    void remove(int id);

    List<Integer> getHistory();
}
