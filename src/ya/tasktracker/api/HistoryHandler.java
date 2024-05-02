package ya.tasktracker.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.task.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class HistoryHandler extends BaseHandler implements HttpHandler {
    public HistoryHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        List<Task> history = manager.getHistory();
        String jsonHistory = gson.toJson(history);

        exchange.sendResponseHeaders(200, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jsonHistory.getBytes());
        }
    }
}