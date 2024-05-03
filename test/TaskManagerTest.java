import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ya.tasktracker.constants.TaskStatus;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import javax.management.InstanceNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    @BeforeEach
    abstract void create() throws IOException;

    @Test
    void createTaskTest() throws InstanceNotFoundException {
        Task task = new Task("task");
        int id = taskManager.createTask(task);
        assertEquals(taskManager.getTask(id), task);
    }

    @Test
    void createEpicTest() throws InstanceNotFoundException {
        Epic epic = new Epic("epic");
        int id = taskManager.createEpic(epic);
        assertEquals(taskManager.getEpic(id), epic);
    }

    @Test
    void createSubTaskTest() throws InstanceNotFoundException {
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

    @Test
    void getPrioritizedTasksTest() {
        Task task1s = new Task("task1s");
        task1s.setStartTime(ZonedDateTime.now().plusHours(1));
        taskManager.createTask(task1s);
        Task task2s = new Task("task2s");
        taskManager.createTask(task2s);
        SubTask subTask1s = new SubTask("subTask1s");
        subTask1s.setStartTime(ZonedDateTime.now());
        taskManager.createSubTask(subTask1s);

        TreeSet<Task> list = taskManager.getPrioritizedTasks();

        assertEquals(2, list.size());
        assertEquals(subTask1s, list.first());
        assertEquals(task1s, list.last());
    }

    @Test
    void setEpicStatusNewTest() {
        Epic epic = new Epic("epic");
        SubTask subTask1 = new SubTask("subTask1");
        SubTask subTask2 = new SubTask("subTask2");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);

        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        assertEquals(TaskStatus.NEW, epic.getStatus());

    }

    @Test
    void setEpicStatusDoneTest() {
        Epic epic = new Epic("epic");
        SubTask subTask1 = new SubTask("subTask1");
        SubTask subTask2 = new SubTask("subTask2");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        subTask1.setStatus(TaskStatus.DONE);
        subTask2.setStatus(TaskStatus.DONE);
        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);

        assertEquals(TaskStatus.DONE, epic.getStatus());

    }

    @Test
    void setEpicStatusNewDoneTest() {
        Epic epic = new Epic("epic");
        SubTask subTask1 = new SubTask("subTask1");
        SubTask subTask2 = new SubTask("subTask2");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        subTask2.setStatus(TaskStatus.DONE);
        taskManager.updateSubTask(subTask2);

        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());

    }

    @Test
    void setEpicStatusInProgressTest() {
        Epic epic = new Epic("epic");
        SubTask subTask1 = new SubTask("subTask1");
        SubTask subTask2 = new SubTask("subTask2");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        subTask1.setStatus(TaskStatus.IN_PROGRESS);
        subTask2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);

        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());

    }

    @Test
    void EpicSubTaskTest() throws InstanceNotFoundException {
        Epic epic3 = new Epic("epic3");
        int id = taskManager.createEpic(epic3);
        SubTask subTask3 = new SubTask("subTask3");
        int stId = taskManager.createSubTask(subTask3);
        epic3.addSubTask(subTask3);

        Epic epic = taskManager.getEpic(id);

        assertEquals(1, epic.getSubTask().size());
        assertEquals(stId, taskManager.getSubTasks().get(0).getId());
    }

    @Test
    void SubTaskEpicTest() throws InstanceNotFoundException {
        Epic epic3 = new Epic("epic3");
        int id = taskManager.createEpic(epic3);
        SubTask subTask3 = new SubTask("subTask3");
        int stId = taskManager.createSubTask(subTask3);
        epic3.addSubTask(subTask3);

        SubTask subTask = taskManager.getSubtask(stId);

        assertEquals(id, subTask.getParentId());
    }

    @Test
    void intervalNotCrossingTest() {
        Task task = new Task("task1");
        Task task2 = new Task("task2");
        task.setStartTime(ZonedDateTime.of(LocalDateTime.of(2024, 04, 30, 12, 13),
                ZoneId.of("Europe/Moscow")));
        task.setDuration(Duration.ofMinutes(10));
        task2.setStartTime(ZonedDateTime.of(LocalDateTime.of(2024, 04, 29, 12, 13),
                ZoneId.of("Europe/Moscow")));
        task2.setDuration(Duration.ofMinutes(10));

        taskManager.createTask(task);
        int id = taskManager.createTask(task2);

        assertTrue(id > -1);
    }

    @Test
    void intervalCrossingTest() {
        Task task = new Task("task1");
        Task task2 = new Task("task2");
        task.setStartTime(ZonedDateTime.of(LocalDateTime.of(2024, 04, 30, 12, 13),
                ZoneId.of("Europe/Moscow")));
        task.setDuration(Duration.ofMinutes(10));
        task2.setStartTime(ZonedDateTime.of(LocalDateTime.of(2024, 04, 30, 12, 11),
                ZoneId.of("Europe/Moscow")));
        task2.setDuration(Duration.ofMinutes(10));

        taskManager.createTask(task);
        int id = taskManager.createTask(task2);

        assertEquals(-1, id);
    }

}