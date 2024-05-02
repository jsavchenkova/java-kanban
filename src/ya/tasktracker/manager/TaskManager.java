package ya.tasktracker.manager;

import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

/*Управление задачами*/
public interface TaskManager {
    List<Task> getTasks();

    List<Task> getEpics();

    List<Task> getSubTasks();

    void removeAllTasks();

    /*Удаление всех эпиков вместе с подзадачами*/
    void removeAllEpics();

    void removeAllSubTask();

    Task getTask(int id) throws InstanceNotFoundException;

    Epic getEpic(int id) throws InstanceNotFoundException;

    SubTask getSubtask(int id) throws InstanceNotFoundException;

    int createTask(Task task);

    int createEpic(Epic task);

    int createSubTask(SubTask task);

    int updateTask(Task task);

    int updateEpic(Epic task);

    int updateSubTask(SubTask task);

    void deleteTask(int id);

    /*удалине эпика вместе с подзадачами*/
    void deleteEpic(int id);

    void deleteSubTask(int id);

    List<Integer> getSubTasksByEpic(Epic epic);

    /*история просмотра задач*/
    List<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();

    /*true - нет пересекающихся интервалос
    false - найдены пересекающиеся интервалы*/
    boolean checkTimeInterval(Task task);

    boolean quickCheckTimeInterval(Task task);
}
