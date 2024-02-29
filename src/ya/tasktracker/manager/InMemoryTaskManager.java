package ya.tasktracker.manager;

import ya.tasktracker.task.*;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SubTask> subTasks;
    private final InMemoryHistoryManager inMemoryHistoryManager;

    public InMemoryTaskManager(InMemoryHistoryManager historyManager) {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        inMemoryHistoryManager = historyManager;
    }

    public ArrayList<ITask> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<ITask> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<ITask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    public void removeAllSubTask() {
        for (Epic epic : epics.values()) {
            epic.clearSubTasks();
        }
        subTasks.clear();
    }


    public Task getTask(int id) {
        Task task = tasks.get(id);
        if(task != null) {
            inMemoryHistoryManager.add(task);
        }
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if(epic!=null) {
            inMemoryHistoryManager.add(epic);
        }
        return epics.get(id);
    }

    public SubTask getSubtask(int id) {
        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            inMemoryHistoryManager.add(subTask);
        }
        return subTasks.get(id);
    }

    public int createTask(Task task) {
        int id = IndexTask.getNextId();
        task.setId(id);
        tasks.put(task.getId(), task);
        return id;
    }

    public int createEpic(Epic task) {
        int id = IndexTask.getNextId();
        task.setId(id);
        updateEpic(task);
        return id;
    }

    public int createSubTask(SubTask task) {
        int id = IndexTask.getNextId();
        task.setId(id);
        updateSubTask(task);
        return id;
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }
    public void updateEpic(Epic task){
        epics.put(task.getId(), task);
        for(SubTask subTask: task.getSubTask()){
            subTasks.put(subTask.getId(), subTask);
        }
    }
    public void updateSubTask(SubTask task){
        subTasks.put(task.getId(), task);
        if(task.getParentId()!=null) {
            epics.put(task.getParentId(), epics.get(task.getParentId()));
        }
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        Epic epic = epics.get(id);
        for (AbstractTask task : epic.getSubTask()) {
            deleteSubTask(task.getId());
        }
        epics.remove(id);
    }

    public void deleteSubTask(int id) {
        SubTask subTask = subTasks.get(id);
        epics.get(subTask.getParentId()).removeSubtask(subTask.getId());
        subTasks.remove(id);
    }

    public ArrayList<SubTask> getSubTasksByEpic(Epic epic) {
        return new ArrayList<>(epic.getSubTask());
    }

    public ArrayList<ITask> getHistory(){
        return inMemoryHistoryManager.getHistory();
    }

}
