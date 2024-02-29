package ya.tasktracker.test;

import org.junit.jupiter.api.Test;
import ya.tasktracker.task.AbstractTask;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {

    @Test
    void setParentTest() {
        SubTask subTask = new SubTask("subtask_name");
        AbstractTask task = subTask;
        boolean exceprionThrown = false;
        try {
            subTask.setParent( task.getId());
        } catch (ClassCastException e) {
            exceprionThrown = true;
        }
        assertTrue(exceprionThrown);
    }
}