package ya.tasktracker.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ya.tasktracker.manager.InMemoryHistoryManager;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;
import ya.tasktracker.manager.InMemoryTaskManager;
import ya.tasktracker.manager.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private static TaskManager taskManager;

    @BeforeAll
    static void create() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        taskManager = new InMemoryTaskManager(historyManager);
    }

    @Test
    void createTaskTest() {
        Task task = new Task("task");
        int id = taskManager.createTask(task);
        assertEquals(taskManager.getTask(id), task);
    }

    @Test
    void createEpicTest() {
        Epic epic = new Epic("epic");
        int id = taskManager.createEpic(epic);
        assertEquals(taskManager.getEpic(id), epic);
    }

    @Test
    void createSubTaskTest() {
        SubTask subTask = new SubTask("subtask");
        int id = taskManager.createSubTask(subTask);
        assertEquals(taskManager.getSubtask(id), subTask);
    }


}