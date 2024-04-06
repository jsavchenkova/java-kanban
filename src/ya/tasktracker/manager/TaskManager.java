package ya.tasktracker.manager;

import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import java.util.List;

/*Управление задачами*/
public interface TaskManager {
    List<Task> getTasks();

    List<Task> getEpics();

    List<Task> getSubTasks();

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

    List<Integer> getSubTasksByEpic(Epic epic);

    /*история просмотра задач*/
    List<Integer> getHistory();
}
