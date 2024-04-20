package ya.tasktracker.manager;

import java.io.File;
import java.io.IOException;

public class Managers {

    public TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public TaskManager getFileBackedManager() throws IOException {
        return new FileBackedTaskManager(getFileBackedHistoryManager());
    }

    public TaskManager getFileBackedManager(File file) {
        return new FileBackedTaskManager(getFileBackedHistoryManager(), file);
    }

    public HistoryManager getFileBackedHistoryManager() {
        return new FileBackedHistoryManager();
    }

    public HistoryManager getFileBackedHistoryManager(File file) {
        return new FileBackedHistoryManager(file);
    }
}
