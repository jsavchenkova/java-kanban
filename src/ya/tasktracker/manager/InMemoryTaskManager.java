package ya.tasktracker.manager;

import ya.tasktracker.task.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, SubTask> subTasks;
    private final HistoryManager inMemoryHistoryManager;
    private final IndexTask index;

    public InMemoryTaskManager(HistoryManager historyManager) {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        inMemoryHistoryManager = historyManager;
        index = new IndexTask();
    }

    public List<ITask> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<ITask> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<ITask> getSubTasks() {
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
        int id = index.getNextId();
        task.setId(id);
        tasks.put(task.getId(), task);
        return id;
    }

    public int createEpic(Epic task) {
        int id = index.getNextId();
        task.setId(id);
        updateEpic(task);
        return id;
    }

    public int createSubTask(SubTask task) {
        int id = index.getNextId();
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

    public List<SubTask> getSubTasksByEpic(Epic epic) {
        return new ArrayList<>(epic.getSubTask());
    }

    public List<ITask> getHistory(){
        return inMemoryHistoryManager.getHistory();
    }

}
