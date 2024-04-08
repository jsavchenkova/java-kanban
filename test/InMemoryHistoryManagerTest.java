import org.junit.jupiter.api.Test;
import ya.tasktracker.manager.HistoryManager;
import ya.tasktracker.manager.Managers;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    @Test
    void addTaskTest() {
        Task task1 = new Task("1");
        Task task2 = new Task("2");
        Epic task3 = new Epic("3");
        Managers managers = new Managers();
        TaskManager taskManager = managers.getDefault();
        int id1 = taskManager.createTask(task1);
        int id2 = taskManager.createTask(task2);
        int id3 = taskManager.createEpic(task3);
        taskManager.getTask(id2);
        taskManager.getTask(id1);
        taskManager.getEpic(id3);


        assertEquals(taskManager.getHistory().get(0).getId(), id2);
        assertEquals(taskManager.getHistory().get(1).getId(), id1);
        assertEquals(taskManager.getHistory().get(2).getId(), id3);
    }

    @Test
    void removeTest() {
        Task task = new Task("4");
        Managers managers = new Managers();
        TaskManager taskManager = managers.getDefault();
        int id = taskManager.createTask(task);
        HistoryManager historyManager = managers.getDefaultHistory();
        historyManager.add(task);

        historyManager.remove(id);

        assertEquals(0, historyManager.getHistory().size());

    }
}