package ya.tasktracker;

public class Managers {
    private final InMemoryTaskManager inMemoryTaskManager;

    public Managers() {
        this.inMemoryTaskManager = new InMemoryTaskManager();
    }

    public TaskManager getDefault(){
        return inMemoryTaskManager;
    }
}
