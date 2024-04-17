import org.junit.jupiter.api.BeforeEach;
import ya.tasktracker.manager.Managers;
import ya.tasktracker.manager.TaskManager;

import java.io.IOException;

class FileBackeHistoryManagerTest<FileBackedHistoryManager> extends HistoryManagerTest {
    @Override
    @BeforeEach
    void create() throws IOException {
        Managers managers = new Managers();
        taskManager = managers.getFileBackedManager();
        historyManager = managers.getFileBackedHistoryManager();
    }
}
