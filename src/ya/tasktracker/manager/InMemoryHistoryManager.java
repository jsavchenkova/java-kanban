package ya.tasktracker.manager;

import ya.tasktracker.task.AbstractTask;
import ya.tasktracker.task.ITask;

import java.util.ArrayList;

public final class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<ITask> taskList ;

    public  InMemoryHistoryManager(){
        taskList = new ArrayList<>(10);
    }


    @Override
    public void add(ITask task) {
        // если список заполнен полностью, удаляем первый элемент
        if(taskList.size()==10){
            taskList.remove(0);
        }
        taskList.add(task);
    }

    @Override
    public ArrayList<ITask> getHistory() {
        return taskList;
    }
}
