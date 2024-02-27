package ya.tasktracker;

import java.util.ArrayList;

public interface TaskManager {
    ArrayList<Task> getTasks();
    ArrayList<Epic> getEpics();
    ArrayList<SubTask> getSubTasks();
    void removeAllTasks();
    void removeAllEpics();
    void removeAllSubTask();
    Task getTask(int id);
    Epic getEpic(int id);
    SubTask getSubtask(int id);
    void createTask(Task task);
    void createEpic(Epic task);
    void createSubTask(SubTask task);
    void updateTask(Task task);
    void updateEpic(Epic task);
    void updateSubTask(SubTask task);
    void deleteTask(int id);
    void deleteEpic(int id);
    void deleteSubTask(int id);
    ArrayList<SubTask> getSubTasksByEpic(Epic epic);
    HistoryManager getDefaultHistory();
}
