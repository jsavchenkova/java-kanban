package ya.tasktracker.manager;

import ya.tasktracker.constants.TaskType;
import ya.tasktracker.exceptions.ManagerSaveException;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import javax.management.InstanceNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Менеджер задач, который хранит своё состояние в файле, и может быть восстановлен из файла
public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File taskFile;

    public FileBackedTaskManager(HistoryManager historyManager) throws IOException {
        super(historyManager);
        taskFile = createFile(Paths.get("resources", "default_task_file.csv")).toFile();
    }

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        taskFile = createFile(file.toPath()).toFile();
    }

    public static FileBackedTaskManager loadFromFile(File file, File fileHistory) throws IOException, InstanceNotFoundException {
        int maxId = 0;
        FileBackedHistoryManager historyManager = new FileBackedHistoryManager(fileHistory);
        FileBackedTaskManager manager = new FileBackedTaskManager(historyManager, file);
        List<String> lines = Files.readAllLines(Paths.get(file.toURI()));
        if (lines.size() < 2) {
            manager.save();
            historyManager.save();
            return manager;
        }
        lines.remove(0);
        for (String l : lines) {
            String[] str = l.split(",", -1);
            if (maxId < Integer.parseInt(str[0])) {
                maxId = Integer.parseInt(str[0]);
            }
            switch (TaskType.valueOf(str[1])) {
                case TASK:
                    Task task = new Task(str);
                    manager.tasks.put(task.getId(), task);
                    break;
                case EPIC:
                    Epic epic = new Epic(str);
                    manager.epics.put(epic.getId(), epic);
                    break;
                case SUBTASK:
                    SubTask subTask = new SubTask(str);
                    manager.subTasks.put(subTask.getId(), subTask);
                    Epic parentEpic = manager.epics.get(subTask.getParentId());
                    parentEpic.getSubTask().add(subTask.getId());
                    parentEpic.addSubTask(subTask);
                    break;
            }
        }
        manager.indexTask.setId(maxId + 1);
        // Считываем историю из файла
        String str = Files.readString(Paths.get(fileHistory.toURI()));
        if (str.isBlank()) {
            return manager;
        }
        String[] history = str.split(",");
        for (String h : history) {
            if (manager.tasks.containsKey(Integer.parseInt(h))) {
                manager.getTask(Integer.parseInt(h));
                continue;
            }
            if (manager.epics.containsKey(Integer.parseInt(h))) {
                manager.getEpic(Integer.parseInt(h));
                continue;
            }
            if (manager.subTasks.containsKey(Integer.parseInt(h))) {
                manager.getSubtask(Integer.parseInt(h));
            }
        }
        return manager;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(taskFile)) {


            writer.append("id,type,name,status,description,epic,start,finish,duration\n");
            writer.append(String.join(",", getTasks().stream()
                    .map(Task::serializeToString).collect(Collectors.joining("\n"))));
            writer.append("\n");
            writer.append(String.join(",", getEpics().stream()
                    .map(Task::serializeToString).collect(Collectors.joining("\n"))));
            writer.append("\n");
            writer.append(String.join(",", getSubTasks().stream()
                    .map(Task::serializeToString).collect(Collectors.joining("\n"))));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubTask() {
        super.removeAllSubTask();
        save();
    }

    @Override
    public Task getTask(int id) throws InstanceNotFoundException {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) throws InstanceNotFoundException {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public SubTask getSubtask(int id) throws InstanceNotFoundException {
        SubTask subTask = super.getSubtask(id);
        save();
        return subTask;
    }

    @Override
    public int createTask(Task task) {
        int id = super.createTask(task);
        save();
        return id;
    }

    @Override
    public int createEpic(Epic task) {
        int id = super.createEpic(task);
        save();
        return id;
    }

    @Override
    public int createSubTask(SubTask task) {
        int id = super.createSubTask(task);
        save();
        return id;
    }

    @Override
    public int updateTask(Task task) {
        super.updateTask(task);
        save();
        return task.getId();
    }

    @Override
    public int updateEpic(Epic task) {
        int id = super.updateEpic(task);
        save();
        return id;
    }

    @Override
    public int updateSubTask(SubTask task) {
        super.updateSubTask(task);
        save();
        return task.getId();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubTask(int id) {
        super.deleteSubTask(id);
        save();
    }

    private Path createFile(Path path) {
        try {
            if (!Files.exists(path)) {
                path = Files.createFile(path);
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.getCause());
        }
        return path;
    }
}
