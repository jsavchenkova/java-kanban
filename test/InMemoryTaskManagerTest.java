import org.junit.jupiter.api.BeforeEach;
import ya.tasktracker.manager.Managers;

class InMemoryTaskManagerTest extends TaskManagerTest{
    @Override
    @BeforeEach
    void create() {
        Managers managers = new Managers();
        taskManager = managers.getDefault();
    }
}
