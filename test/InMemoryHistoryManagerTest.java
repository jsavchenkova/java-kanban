import org.junit.jupiter.api.BeforeEach;
import ya.tasktracker.manager.Managers;
import ya.tasktracker.manager.TaskManager;

class InMemoryHistoryManagerTest<InMemoryHistoryManager> extends HistoryManagerTest{
    @Override
    @BeforeEach
    void create() {
        Managers managers = new Managers();
        taskManager = managers.getDefault();
        historyManager = managers.getDefaultHistory();
    }
}
