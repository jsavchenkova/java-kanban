package ya.tasktracker.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ya.tasktracker.manager.InMemoryHistoryManager;
import ya.tasktracker.manager.InMemoryTaskManager;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    void create() {
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

    @Test
    void getTasksTest() {
        Task task = new Task("task1");
        Task task2 = new Task("task2");
        taskManager.createTask(task);
        taskManager.createTask(task2);

        List<Task> list = taskManager.getTasks();

        assertEquals(2, list.size());
    }

    @Test
    void getEpicsTest() {
        Epic epic1 = new Epic("epic1");
        Epic epic2 = new Epic("epic2");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        List<Task> list = taskManager.getEpics();

        assertEquals(2, list.size());
    }

    @Test
    void getSubTasTest() {
        SubTask subTask1 = new SubTask("subTask1");
        SubTask subTask2 = new SubTask("subTask2");
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);

        List<Task> list = taskManager.getSubTasks();

        assertEquals(2, list.size());
    }

    @Test
    void removeAllEpicsTest() {
        Epic epic3 = new Epic("epic3");
        Epic epic4 = new Epic("epic4");
        taskManager.createEpic(epic3);
        taskManager.createEpic(epic4);
        SubTask subTask3 = new SubTask("subTask3");
        SubTask subTask4 = new SubTask("subTask4");
        epic3.addSubTask(subTask3);
        epic4.addSubTask(subTask4);

        taskManager.removeAllEpics();

        assertEquals(0, taskManager.getEpics().size());
        assertEquals(0, taskManager.getSubTasks().size());
        assertEquals(0, taskManager.getHistory().size());
    }

    @Test
    void removeAllSubTasksTest() {
        Epic epic5 = new Epic("epic5");
        taskManager.createEpic(epic5);
        SubTask subTask5 = new SubTask("subTask5");
        SubTask subTask6 = new SubTask("subTask6");
        epic5.addSubTask(subTask5);
        epic5.addSubTask(subTask6);

        taskManager.removeAllSubTask();

        assertEquals(0, taskManager.getSubTasks().size());
        assertEquals(0, epic5.getSubTask().size());
        assertEquals(0, taskManager.getHistory().size());
    }

}