package ya.tasktracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private static TaskManager taskManager;

    @BeforeAll
    static void create(){
        taskManager = new InMemoryTaskManager();
    }
    @Test
    void createTaskTest() {
        Task task = new Task("task");
        int id = task.getId();
        taskManager.createTask(task);
        assertEquals(taskManager.getTask(id), task);
    }

    @Test
    void createEpicTest() {
        Epic epic = new Epic("epic");
        int id = epic.getId();
        taskManager.createEpic(epic);
        assertEquals(taskManager.getEpic(id), epic);
    }

    @Test
    void createSubTaskTest() {
        SubTask subTask = new SubTask("subtask");
        int id = subTask.getId();
        taskManager.createSubTask(subTask);
        assertEquals(taskManager.getSubtask(id),subTask);
    }


}