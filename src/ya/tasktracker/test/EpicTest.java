package ya.tasktracker.test;

import org.junit.jupiter.api.Test;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void equalsTaskEpicTest() {
        Epic task3 = new Epic("epic_test");
        Task task4 = task3;
        assertTrue(task3.equals(task4));
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
    void addSubTaskTest(){
        Epic epic = new Epic("epic_test_subTask");
        epic.setId(62);
        SubTask subTask = new SubTask("test_subTask");
        subTask.setId(61);

        epic.addSubTask(subTask);

        assertTrue(epic.getSubTask().contains(subTask.getId()));
        assertEquals(subTask.getParentId(), epic.getId());
    }

    @Test
    void getSubTask(){
        Epic epic = new Epic("epic_get_subtask");
        epic.setId(63);
        SubTask subTask64 = new SubTask("test_get_subtask");
        subTask64.setId(64);
        SubTask subTask65 = new SubTask("test_get_subtask1");
        subTask65.setId(65);
        epic.addSubTask(subTask64);
        epic.addSubTask(subTask65);

        List<Integer> listSubTask = epic.getSubTask();
        assertEquals(2,listSubTask.size());

    }

    @Test
    void clearSubTasks(){
        Epic epic = new Epic("epic_clear_subtask");
        epic.setId(66);
        SubTask subTask67 = new SubTask("test_clear_subtask");
        subTask67.setId(67);
        SubTask subTask68 = new SubTask("test_clear_subtask1");
        subTask68.setId(68);
        epic.addSubTask(subTask67);
        epic.addSubTask(subTask68);

        epic.clearSubTasks();

        assertEquals(0, epic.getSubTask().size());
    }

    @Test
    void removeSubTaskTest(){
        Epic epic = new Epic("epic_clear_subtask");
        epic.setId(69);
        SubTask subTask70 = new SubTask("test_clear_subtask");
        subTask70.setId(70);
        SubTask subTask71 = new SubTask("test_clear_subtask1");
        subTask71.setId(71);
        epic.addSubTask(subTask70);
        epic.addSubTask(subTask71);

        epic.removeSubtask(70);

        assertEquals(1, epic.getSubTask().size());
        assertFalse(epic.getSubTask().contains(70));
    }
}