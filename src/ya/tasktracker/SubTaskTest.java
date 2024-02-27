package ya.tasktracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {

    @Test
    void setParentTest() {
        SubTask subTask = new SubTask("subtask_name");
        AbstractTask task = subTask;
        boolean exceprionThrown = false;
        try {
            subTask.setParent((Epic) task);
        } catch (ClassCastException e) {
            exceprionThrown = true;
        }
        assertTrue(exceprionThrown);
    }
}