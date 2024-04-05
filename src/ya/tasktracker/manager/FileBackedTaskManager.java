package ya.tasktracker.manager;

import ya.tasktracker.constants.TaskType;
import ya.tasktracker.exceptions.ManagerSaveException;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File taskFile;

    public FileBackedTaskManager(HistoryManager historyManager) throws IOException {
        super(historyManager);
        taskFile = createFile(Paths.get("src", "resources", "default_task_file.csv")).toFile();
    }

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        taskFile = createFile(file.toPath()).toFile();
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

    public static FileBackedTaskManager loadFromFile(File file, File fileHistory) throws IOException {
        FileBackedHistoryManager historyManager = FileBackedHistoryManager.loadFromFile(fileHistory);
        FileBackedTaskManager manager = new FileBackedTaskManager(historyManager,file);
        List<String> lines = Files.readAllLines(Paths.get(file.toURI()));
        if (lines.size() < 2) {
            manager.save();
            historyManager.save();
            return manager;
        }
        lines.remove(0);
        for (String l : lines) {
            String[] str = l.split(",", -1);

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
                    break;
            }
        }
        return manager;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(taskFile)) {


            writer.append("id,type,name,status,description,epic\n");
            for (Task t : getTasks()) {
                writer.append(t.serializeToString());
                writer.append("\n");
            }
            for (Task ep : getEpics()) {
                writer.append(ep.serializeToString());
                writer.append("\n");
            }
            for (Task s : getSubTasks()) {
                writer.append(((SubTask) s).serializeToString());
                writer.append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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

    public Task getTask(UUID id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    public Epic getEpic(UUID id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    public SubTask getSubtask(UUID id) {
        SubTask subTask = super.getSubtask(id);
        save();
        return subTask;
    }

    public UUID createTask(Task task) {
        UUID id = super.createTask(task);
        save();
        return id;
    }

    public UUID createEpic(Epic task) {
        UUID id = super.createEpic(task);
        save();
        return id;
    }

    public UUID createSubTask(SubTask task) {
        UUID id = super.createSubTask(task);
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

    public void deleteTask(UUID id) {
        super.deleteTask(id);
        save();
    }

    public void deleteEpic(UUID id) {
        super.deleteEpic(id);
        save();
    }

    public void deleteSubTask(UUID id) {
        super.deleteSubTask(id);
        save();
    }
}
