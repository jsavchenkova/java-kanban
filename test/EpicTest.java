import org.junit.jupiter.api.Test;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void equalsTaskEpicTest() {
        Epic task3 = new Epic("epic_test");
        Task task4 = task3;

        assertEquals(task3, task4);
    }

    @Test
    void addEpicTest() {
        Epic epic = new Epic("epic_test");
        Task task = epic;
        boolean exceprionThrown = false;
        try {
            epic.addSubTask((SubTask) task);
        } catch (ClassCastException e) {
            exceprionThrown = true;
        }
        assertTrue(exceprionThrown);
    }

    @Test
    void addSubTaskTest() {
        Epic epic = new Epic("epic_test_subTask");
        int id1 = 1;
        int id2 = 2;
        epic.setId(id1);
        SubTask subTask = new SubTask("test_subTask");
        subTask.setId(id2);

        epic.addSubTask(subTask);

        assertTrue(epic.getSubTask().contains(subTask.getId()));
        assertEquals(subTask.getParentId(), epic.getId());
    }

    @Test
    void getSubTask() {
        int id1 = 3;
        int id2 = 4;
        int id3 = 5;
        Epic epic = new Epic("epic_get_subtask");
        epic.setId(id1);
        SubTask subTask64 = new SubTask("test_get_subtask");
        subTask64.setId(id2);
        SubTask subTask65 = new SubTask("test_get_subtask1");
        subTask65.setId(id3);
        epic.addSubTask(subTask64);
        epic.addSubTask(subTask65);

        List<Integer> listSubTask = epic.getSubTask();
        assertEquals(2, listSubTask.size());

    }

    @Test
    void clearSubTasks() {
        int id1 = 6;
        int id2 = 7;
        int id3 = 8;
        Epic epic = new Epic("epic_clear_subtask");
        epic.setId(id1);
        SubTask subTask67 = new SubTask("test_clear_subtask");
        subTask67.setId(id2);
        SubTask subTask68 = new SubTask("test_clear_subtask1");
        subTask68.setId(id3);
        epic.addSubTask(subTask67);
        epic.addSubTask(subTask68);

        epic.clearSubTasks();

        assertEquals(0, epic.getSubTask().size());
    }

    @Test
    void removeSubTaskTest() {
        int id1 = 9;
        int id2 = 10;
        int id3 = 11;
        Epic epic = new Epic("epic_clear_subtask");
        epic.setId(id1);
        SubTask subTask70 = new SubTask("test_clear_subtask");
        subTask70.setId(id2);
        SubTask subTask71 = new SubTask("test_clear_subtask1");
        subTask71.setId(id3);
        epic.addSubTask(subTask70);
        epic.addSubTask(subTask71);

        epic.removeSubtask(id2);

        assertEquals(1, epic.getSubTask().size());
        assertFalse(epic.getSubTask().contains(id2));
    }
}