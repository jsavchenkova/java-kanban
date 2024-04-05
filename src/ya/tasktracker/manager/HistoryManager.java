package ya.tasktracker.manager;

import java.util.List;
import java.util.UUID;

public interface HistoryManager {
    void add(UUID task);

    void remove(UUID id);

    List<UUID> getHistory();
}
