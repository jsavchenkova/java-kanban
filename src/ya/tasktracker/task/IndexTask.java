package ya.tasktracker.task;

public class IndexTask {
    private int id;

    public int getNextId() {
        return id++;
    }
}
