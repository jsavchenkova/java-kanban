package ya.tasktracker.test;

import org.junit.jupiter.api.Test;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void equalsTaskEpicTest() {
        Epic task3 = new Epic("epic_test");
        Task task4 = task3;
        assertTrue(task3.equals(task4));
    }

    @Test
    void addEpicTest(){
        Epic epic = new Epic("epic_test");
        Task task = epic;
        boolean exceprionThrown = false;
        try {
            epic.addSubTask((SubTask) task);
        }catch(ClassCastException e){
            exceprionThrown = true;
        }
        assertTrue(exceprionThrown);
    }
}