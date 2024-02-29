package ya.tasktracker.test;

import org.junit.jupiter.api.Test;
import ya.tasktracker.task.Task;
import ya.tasktracker.manager.InMemoryTaskManager;
import ya.tasktracker.manager.Managers;
import ya.tasktracker.manager.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultTest() {
        Managers managers = new Managers();
        assertEquals(managers.getDefault().getClass(), InMemoryTaskManager.class);

        TaskManager taskManager = managers.getDefault();
        Task task = new Task("name");
        int id = taskManager.createTask(task);
        assertEquals(taskManager.getTask(id), task);
    }
}