import ya.tasktracker.manager.HistoryManager;
import ya.tasktracker.manager.Managers;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.task.Epic;
import ya.tasktracker.manager.InMemoryTaskManager;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Managers managers = new Managers();
        TaskManager taskManager = managers.getDefault();
        System.out.println(taskManager.getHistory());
        int taskId1 = taskManager.createTask(new Task("первая задача"));
        System.out.println(taskManager.getHistory());
        int taskId2 = taskManager.createTask(new Task("вторая задача"));
        System.out.println(taskManager.getHistory());
        int epicId2 = taskManager.createEpic(new Epic("пустой эпик"));
        System.out.println(taskManager.getHistory());
        int epicId = taskManager.createEpic(new Epic("эпик с подзадачами"));
        System.out.println(taskManager.getHistory());
        Epic epic = taskManager.getEpic(epicId);
        System.out.println(taskManager.getHistory());
        epic.addSubTask(taskManager.getSubtask(taskManager.createSubTask(new SubTask("первая подзадача"))));
        System.out.println(taskManager.getHistory());
        epic.addSubTask(taskManager.getSubtask(taskManager.createSubTask(new SubTask("вторая подзадача"))));
        System.out.println(taskManager.getHistory());
        epic.addSubTask(taskManager.getSubtask(taskManager.createSubTask(new SubTask("третья подзадача"))));
        System.out.println(taskManager.getHistory());
        taskManager.getTask(taskId2);
        System.out.println(taskManager.getHistory());
        taskManager.getEpic(epicId2);
        System.out.println(taskManager.getHistory());
        taskManager.getEpic(epicId);
        System.out.println(taskManager.getHistory());
        taskManager.getSubtask(epic.getSubTask().get(2));
        taskManager.getSubtask(epic.getSubTask().get(0));
        System.out.println(taskManager.getHistory());
        taskManager.deleteTask(taskId2);
        System.out.println(taskManager.getHistory());
        taskManager.deleteEpic(epicId);
        System.out.println(taskManager.getHistory());



    }
}
