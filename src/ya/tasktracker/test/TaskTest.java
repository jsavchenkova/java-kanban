package ya.tasktracker.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ya.tasktracker.task.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private static Task task1;

    @BeforeAll
    public static void beforeAll() {
        task1 = new Task("test_task");
    }

    @Test
    void equalsTest() {
        task1 = new Task("test_task");
        Task task2 = task1;

        assertTrue(task1.equals(task2));
    }

    @Test
    void equalsTaskTaskTest() {
        Task task3 = task1;
        assertTrue(task1.equals(task3));
    }


}