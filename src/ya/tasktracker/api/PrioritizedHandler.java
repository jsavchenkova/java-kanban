package ya.tasktracker.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.task.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.util.TreeSet;

// обработчик эндпоинта /prioritized
public class PrioritizedHandler extends BaseHandler implements HttpHandler {
    public PrioritizedHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        TreeSet<Task> treeSet = manager.getPrioritizedTasks();
        String jsonTreeSet = gson.toJson(treeSet);
        exchange.sendResponseHeaders(200, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jsonTreeSet.getBytes());
        }
    }
}
