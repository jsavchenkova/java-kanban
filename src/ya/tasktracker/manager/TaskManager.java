package ya.tasktracker.manager;

import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import java.util.List;
import java.util.UUID;

/*Управление задачами*/
public interface TaskManager {
    List<Task> getTasks();

    List<Task> getEpics();

    List<Task> getSubTasks();

    void removeAllTasks();

    /*Удаление всех эпиков вместе с подзадачами*/
    void removeAllEpics();

    void removeAllSubTask();

    Task getTask(UUID id);

    Epic getEpic(UUID id);

    SubTask getSubtask(UUID id);

    UUID createTask(Task task);

    UUID createEpic(Epic task);

    UUID createSubTask(SubTask task);

    void updateTask(Task task);

    void updateEpic(Epic task);

    void updateSubTask(SubTask task);

    void deleteTask(UUID id);

    /*удалине эпика вместе с подзадачами*/
    void deleteEpic(UUID id);

    void deleteSubTask(UUID id);

    List<UUID> getSubTasksByEpic(Epic epic);

    /*история просмотра задач*/
    List<UUID> getHistory();
}
