package ya.tasktracker.test;

import org.junit.jupiter.api.Test;
import ya.tasktracker.task.AbstractTask;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void equalsAbstractTaskEpicTest() {
        Epic task3 = new Epic("epic_test");
        AbstractTask task4 = task3;
        assertTrue(task3.equals(task4));
    }

    @Test
    void addEpicTest(){
        Epic epic = new Epic("epic_test");
        AbstractTask task = epic;
        boolean exceprionThrown = false;
        try {
            epic.addSubTask((SubTask) task);
        }catch(ClassCastException e){
            exceprionThrown = true;
        }
        assertTrue(exceprionThrown);
    }
}