package ya.tasktracker;

import java.util.ArrayList;

public final class InMemoryHistoryManager implements HistoryManager{
    private final ArrayList<AbstractTask> taskList ;

    public  InMemoryHistoryManager(){
        taskList = new ArrayList<>(10);
    }

    @Override
    public void add(AbstractTask task) {
        if(taskList.size()==10){
            taskList.remove(0);
        }
        taskList.add(task);
    }

    @Override
    public ArrayList<AbstractTask> getHistory() {
        return taskList;
    }
}
