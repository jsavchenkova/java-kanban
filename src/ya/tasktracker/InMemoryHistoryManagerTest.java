package ya.tasktracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void addTask() {
        Task task1 = new Task("1");
        int id1 = task1.getId();
        Task task2 = new Task("2");
        int id2 = task2.getId();
        Epic task3 = new Epic("3");
        int id3 = task3.getId();

        TaskManager taskManager = new InMemoryTaskManager();
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createEpic(task3);
        taskManager.getTask(id2);
        taskManager.getTask(id1);
        taskManager.getEpic(id3);
        ArrayList<AbstractTask> history = taskManager.getDefaultHistory().getHistory();

        assertEquals(history.get(0), task2);
        assertEquals(history.get(1), task1);
        assertEquals(history.get(2), task3);
    }
}