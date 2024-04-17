package ya.tasktracker.manager;

import ya.tasktracker.exceptions.ManagerSaveException;
import ya.tasktracker.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

// Менеджер истории, который хранит своё состояние в файле, и может быть восстановлен из файла
class FileBackedHistoryManager extends InMemoryHistoryManager {
    private final File historyFile;

    public FileBackedHistoryManager() {
        super();
        historyFile = createFile(Paths.get("src", "resources", "default_history_file.csv")).toFile();
    }

    public FileBackedHistoryManager(File file) {
        super();
        historyFile = createFile(file.toPath()).toFile();
    }

    @Override
    public void add(Task task) {
        super.add(task);
        save();
    }

    @Override
    public void remove(int id) {
        super.remove(id);
        save();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(historyFile)) {
            List<Task> tasks = getHistory();
            writer.append(String.join(",", tasks.stream().map(Task::getId)
                    .map(String::valueOf).collect(Collectors.toList())));

        } catch (IOException e) {
            throw new ManagerSaveException(e.getCause());
        }
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
