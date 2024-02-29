package ya.tasktracker.manager;

public class Managers {
    private final InMemoryTaskManager inMemoryTaskManager;
    private final InMemoryHistoryManager inMemoryHistoryManager;

    public Managers() {
        this.inMemoryHistoryManager = new InMemoryHistoryManager();
        this.inMemoryTaskManager = new InMemoryTaskManager(this.inMemoryHistoryManager);

    }

    public TaskManager getDefault(){
        return inMemoryTaskManager;
    }
    public HistoryManager getDefaultHistory(){
        return inMemoryHistoryManager;
    }
}
