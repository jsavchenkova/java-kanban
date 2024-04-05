package ya.tasktracker.manager;

import ya.tasktracker.exceptions.ManagerSaveException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    public static FileBackedHistoryManager loadFromFile(File fileHistory) throws IOException {
        FileBackedHistoryManager historyManager = new FileBackedHistoryManager(fileHistory);
        String str = Files.readString(Paths.get(fileHistory.toURI()));
        if (str.isBlank()) {
            historyManager.save();
            return historyManager;
        }
        String[] history = str.split(",");
        for (String h : history) {
            historyManager.add(UUID.fromString(h));
        }
        return historyManager;
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

    @Override
    public void add(UUID task) {
        super.add(task);
        save();
    }

    @Override
    public void remove(UUID id) {
        super.remove(id);
        save();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(historyFile)) {
            List<UUID> tasks = getHistory();
            StringBuilder builder = new StringBuilder();
            for (UUID t : tasks) {
                builder.append(t);
                builder.append(",");
            }
            writer.append(builder.toString());
        } catch (IOException e) {
            throw new ManagerSaveException(e.getCause());
        }
    }
}
