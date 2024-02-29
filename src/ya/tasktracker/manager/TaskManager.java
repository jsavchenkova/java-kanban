package ya.tasktracker.manager;

import ya.tasktracker.task.Epic;
import ya.tasktracker.task.ITask;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import java.util.ArrayList;

/*Управление задачами*/
public interface TaskManager {
    ArrayList<ITask> getTasks();
    ArrayList<ITask> getEpics();
    ArrayList<ITask> getSubTasks();
    void removeAllTasks();
    /*Удаление всех эпиков вместе с подзадачами*/
    void removeAllEpics();
    void removeAllSubTask();
    Task getTask(int id);
    Epic getEpic(int id);
    SubTask getSubtask(int id);
    int createTask(Task task);
    int createEpic(Epic task);
    int createSubTask(SubTask task);
    void updateTask(Task task);
    void updateEpic(Epic task);
    void updateSubTask(SubTask task);
    void deleteTask(int id);
    /*удалине эпика вместе с подзадачами*/
    void deleteEpic(int id);
    void deleteSubTask(int id);
    ArrayList<SubTask> getSubTasksByEpic(Epic epic);
    /*история просмотра задач*/
    ArrayList<ITask> getHistory();
}
