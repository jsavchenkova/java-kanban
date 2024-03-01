package ya.tasktracker.test;

import org.junit.jupiter.api.Test;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SubTaskTest {

    @Test
    void setParentTest() {
        SubTask subTask = new SubTask("subtask_name");
        Task task = subTask;
        boolean exceprionThrown = false;
        try {
            subTask.setParent((Epic) task);
        } catch (ClassCastException e) {
            exceprionThrown = true;
        }
        assertTrue(exceprionThrown);
    }
}