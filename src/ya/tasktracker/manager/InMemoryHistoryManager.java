package ya.tasktracker.manager;

import ya.tasktracker.task.Task;

import java.util.ArrayList;

public final class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> taskList ;

    public  InMemoryHistoryManager(){
        taskList = new ArrayList<>(10);
    }


    @Override
    public void add(Task task) {
        // если список заполнен полностью, удаляем первый элемент
        if(taskList.size()==10){
            taskList.remove(0);
        }
        taskList.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {

        return new ArrayList<>(taskList);
    }
}
