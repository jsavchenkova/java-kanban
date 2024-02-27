package ya.tasktracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultTest() {
        Managers managers = new Managers();
        assertEquals(managers.getDefault().getClass(),InMemoryTaskManager.class);

        TaskManager taskManager = managers.getDefault();
        Task task = new Task("name");
        int id = task.getId();
        taskManager.createTask(task);
        assertEquals(taskManager.getTask(id), task);
    }
}