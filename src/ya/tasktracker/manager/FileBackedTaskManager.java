package ya.tasktracker.manager;

import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import java.io.FileWriter;
import java.io.IOException;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final FileWriter writer;

    public FileBackedTaskManager(HistoryManager historyManager) {
        super(historyManager);
        try {
            writer = new FileWriter("task_file.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        System.out.println("save");
    }

    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    public void removeAllSubTask() {
        super.removeAllSubTask();
        save();
    }

    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    public SubTask getSubtask(int id) {
        SubTask subTask = super.getSubtask(id);
        save();
        return subTask;
    }

    public int createTask(Task task) {
        int id = super.createTask(task);
        save();
        return id;
    }

    public int createEpic(Epic task) {
        int id = super.createEpic(task);
        save();
        return id;
    }

    public int createSubTask(SubTask task) {
        int id = super.createSubTask(task);
        save();
        return id;
    }

    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    public void updateSubTask(SubTask task) {
        super.updateSubTask(task);
        save();
    }

    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    public void deleteSubTask(int id) {
        super.deleteSubTask(id);
        save();
    }
}
