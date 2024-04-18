import org.junit.jupiter.api.Test;
import ya.tasktracker.task.Task;
import ya.tasktracker.manager.Managers;
import ya.tasktracker.manager.TaskManager;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultTest() {
        Managers managers = new Managers();

        TaskManager taskManager = managers.getDefault();
        Task task = new Task("name");
        int id = taskManager.createTask(task);

        assertEquals(taskManager.getTask(id).get(), task);
    }
}