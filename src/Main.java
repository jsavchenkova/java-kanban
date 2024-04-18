import ya.tasktracker.manager.FileBackedTaskManager;
import ya.tasktracker.manager.Managers;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Поехали!");

        Managers managers = new Managers();

        File taskFile = Paths.get("src", "resources", "1t.csv").toFile();
        File historyFile = Paths.get("src", "resources", "1h.csv").toFile();
        TaskManager taskManager = FileBackedTaskManager.loadFromFile(taskFile, historyFile);
        System.out.println(taskManager.getHistory());
        int taskId1 = taskManager.createTask(new Task("первая задача"));
        System.out.println(taskManager.getHistory());
        int taskId2 = taskManager.createTask(new Task("вторая задача"));
        System.out.println(taskManager.getHistory());
        int epicId2 = taskManager.createEpic(new Epic("пустой эпик"));
        System.out.println(taskManager.getHistory());
        int epicId = taskManager.createEpic(new Epic("эпик с подзадачами"));
        System.out.println(taskManager.getHistory());
        Epic epic = taskManager.getEpic(epicId).get();
        System.out.println(taskManager.getHistory());
        epic.addSubTask(taskManager.getSubtask(taskManager.createSubTask(new SubTask("первая подзадача"))).get());
        System.out.println(taskManager.getHistory());
        epic.addSubTask(taskManager.getSubtask(taskManager.createSubTask(new SubTask("вторая подзадача"))).get());
        System.out.println(taskManager.getHistory());
        epic.addSubTask(taskManager.getSubtask(taskManager.createSubTask(new SubTask("третья подзадача"))).get());
        taskManager.getEpic(epicId);
        System.out.println(taskManager.getHistory());

        taskFile = Paths.get("src", "resources", "empty.csv").toFile();
        historyFile = Paths.get("src", "resources", "empty_history.csv").toFile();
        TaskManager taskManager1 = FileBackedTaskManager.loadFromFile(taskFile, historyFile);
        System.out.println(taskManager1.getHistory());
        taskId1 = taskManager1.createTask(new Task("первая задача"));
        System.out.println(taskManager1.getHistory());
        taskId2 = taskManager1.createTask(new Task("вторая задача"));
        System.out.println(taskManager1.getHistory());
        epicId2 = taskManager1.createEpic(new Epic("пустой эпик"));
        System.out.println(taskManager1.getHistory());
        epicId = taskManager1.createEpic(new Epic("эпик с подзадачами"));
        System.out.println(taskManager1.getHistory());
        epic = taskManager1.getEpic(epicId).get();
        System.out.println(taskManager1.getHistory());
        epic.addSubTask(taskManager1.getSubtask(taskManager1.createSubTask(new SubTask("первая подзадача"))).get());
        System.out.println(taskManager1.getHistory());
        epic.addSubTask(taskManager1.getSubtask(taskManager1.createSubTask(new SubTask("вторая подзадача"))).get());
        System.out.println(taskManager1.getHistory());
        epic.addSubTask(taskManager1.getSubtask(taskManager1.createSubTask(new SubTask("третья подзадача"))).get());
        System.out.println(taskManager1.getHistory());
        taskManager1.getTask(taskId2);
        System.out.println(taskManager1.getHistory());
        taskManager1.getEpic(epicId2);
        System.out.println(taskManager1.getHistory());
        taskManager1.getEpic(epicId);
        System.out.println(taskManager1.getHistory());
        taskManager1.getSubtask(epic.getSubTask().get(2));
        taskManager1.getSubtask(epic.getSubTask().get(0));
        System.out.println(taskManager1.getHistory());
        taskManager1.deleteTask(taskId2);
        System.out.println(taskManager1.getHistory());
        taskManager1.deleteEpic(epicId);
        System.out.println(taskManager1.getHistory());

        TaskManager taskManager2 = managers.getFileBackedManager();
        System.out.println(taskManager2.getHistory());
        taskId1 = taskManager2.createTask(new Task("первая задача"));
        System.out.println(taskManager2.getHistory());
        taskId2 = taskManager2.createTask(new Task("вторая задача"));
        System.out.println(taskManager2.getHistory());
        epicId2 = taskManager2.createEpic(new Epic("пустой эпик"));
        System.out.println(taskManager2.getHistory());
        epicId = taskManager2.createEpic(new Epic("эпик с подзадачами"));
        System.out.println(taskManager2.getHistory());
        epic = taskManager2.getEpic(epicId).get();
        System.out.println(taskManager2.getHistory());
        epic.addSubTask(taskManager2.getSubtask(taskManager2.createSubTask(new SubTask("первая подзадача"))).get());
        System.out.println(taskManager2.getHistory());
        epic.addSubTask(taskManager2.getSubtask(taskManager2.createSubTask(new SubTask("вторая подзадача"))).get());
        System.out.println(taskManager2.getHistory());
        epic.addSubTask(taskManager2.getSubtask(taskManager2.createSubTask(new SubTask("третья подзадача"))).get());
        System.out.println(taskManager2.getHistory());
        taskManager2.getTask(taskId2);
        System.out.println(taskManager2.getHistory());
        taskManager2.getEpic(epicId2);
        System.out.println(taskManager2.getHistory());
        taskManager2.getEpic(epicId);
        System.out.println(taskManager2.getHistory());
        taskManager2.getSubtask(epic.getSubTask().get(2));
        taskManager2.getSubtask(epic.getSubTask().get(0));
        System.out.println(taskManager2.getHistory());
        taskManager2.deleteTask(taskId2);
        System.out.println(taskManager2.getHistory());
        taskManager2.deleteEpic(epicId);
        System.out.println(taskManager2.getHistory());

    }
}
