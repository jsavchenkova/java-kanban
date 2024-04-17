import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ya.tasktracker.manager.Managers;
import ya.tasktracker.task.Epic;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class FileBackedTaskManagerTest extends TaskManagerTest {
    @Override
    @BeforeEach
    void create() throws IOException {
        Managers managers = new Managers();
        taskManager = managers.getFileBackedManager();
    }

    @Test
    void createEpicWithoutExceptionTest() {
        Epic epic = new Epic("epic");
        assertDoesNotThrow(() -> taskManager.createEpic(epic));
    }

}
