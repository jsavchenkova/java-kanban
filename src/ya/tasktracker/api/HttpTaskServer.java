package ya.tasktracker.api;

import com.sun.net.httpserver.HttpServer;
import ya.tasktracker.manager.FileBackedTaskManager;
import ya.tasktracker.manager.Managers;
import ya.tasktracker.manager.TaskManager;

import javax.management.InstanceNotFoundException;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Paths;

public class HttpTaskServer {
    private static final Integer port = 8080;

    public static void main(String[] args) {
        File taskFile = Paths.get("resources", "1t.csv").toFile();
        File historyFile = Paths.get("resources", "1h.csv").toFile();

        Managers managers = new Managers();
        HttpServer httpServer;
        try {
            TaskManager manager = FileBackedTaskManager.loadFromFile(taskFile, historyFile);


            httpServer = HttpServer.create();
            httpServer.bind(new InetSocketAddress(port), 0);
            httpServer.createContext("/tasks", new TasksHandler(manager));
            httpServer.createContext("/subtasks", new SubTaskHandler(manager));
            httpServer.createContext("/epics", new EpicHandler(manager));
            httpServer.createContext("/history", new HistoryHandler(manager));
            httpServer.createContext("/prioritized", new PrioritizedHandler(manager));
            httpServer.start();
            System.out.println("Start 8080");
        } catch (IOException | InstanceNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
