package ya.tasktracker.task;

public final class IndexTask {
    private static int id;
    public static int getNextId(){
        return id++;
    }
}
