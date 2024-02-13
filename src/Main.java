import ya.tasktracker.Epic;
import ya.tasktracker.Manager;
import ya.tasktracker.SubTask;
import ya.tasktracker.Task;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Manager manager = new Manager();
        Task task1 = new Task("просто задача 1");
        Task task2 = new Task("просто задача 2");
        Epic epic1 = new Epic("эпик 1");
        Epic epic2 = new Epic("эпик 2");
        SubTask subTask1 = new SubTask("подзадача 1");
        SubTask subTask2 = new SubTask("подзадача 2");
        SubTask subTask3 = new SubTask("подзадача 3");
        epic1.addSubTask(subTask1);
        epic1.addSubTask(subTask2);
        epic2.addSubTask(subTask3);
        manager.createTask(task1);
        manager.createTask(task2);
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        epic1.setDescription("Описание");
        manager.updateEpic(epic1);

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTasks());
        System.out.println(manager.getTasks());

        System.out.println("Приехали");
    }
}
