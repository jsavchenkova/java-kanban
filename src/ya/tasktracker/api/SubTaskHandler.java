package ya.tasktracker.api;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import javax.management.InstanceNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

// обработчик эндпоинта /subtasks
public class SubTaskHandler extends BaseHandler implements HttpHandler {

    public SubTaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] str = getPath(exchange);

        switch (method) {
            case "POST":
                putSubTask(exchange);
                break;
            case "GET":
                if (str.length < 3) {
                    // список задач
                    getSubTaskList(exchange);
                } else {
                    getSubTaskById(exchange, str[2]);
                }
                break;
            case "DELETE":
                deleteSubTaskById(exchange, str[2]);
                break;
        }
    }

    private void putSubTask(HttpExchange exchange) throws IOException {
        String request = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        if (request.isBlank()) {
            exchange.sendResponseHeaders(400, 0);
            sendResponse(exchange, "Пустые входные данные");
            return;
        }
        SubTask task;
        try {
            task = gson.fromJson(request, SubTask.class);
        } catch (JsonSyntaxException e) {
            exchange.sendResponseHeaders(400, 0);
            sendResponse(exchange, "Неверный формат входных данных");
            return;
        }
        if (!manager.quickCheckTimeInterval(task)) {
            exchange.sendResponseHeaders(406, 0);
            sendResponse(exchange, "Задача пересекается по времени с уже существующими");
        } else {
            if (task.getId() == null) {
                int id = manager.createSubTask(task);
                exchange.sendResponseHeaders(201, 0);
                sendResponse(exchange, String.format("Задача создана. id = %d", id));
            } else {
                int id = manager.updateSubTask(task);
                exchange.sendResponseHeaders(201, 0);
                sendResponse(exchange, String.format("Задача обновлена. id = %d", id));
            }
        }
    }

    private void getSubTaskList(HttpExchange exchange) throws IOException {
        List<Task> taskList = manager.getSubTasks();
        String taskListJson = gson.toJson(taskList);
        exchange.sendResponseHeaders(200, 0);
        sendResponse(exchange, taskListJson);
    }

    private void getSubTaskById(HttpExchange exchange, String idStr) throws IOException {
        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            exchange.sendResponseHeaders(400, 0);
            sendResponse(exchange, "Неверный формат. Id должен быть числом.");
            return;
        }

        SubTask task;
        try {
            task = manager.getSubtask(id);
        } catch (InstanceNotFoundException e) {
            exchange.sendResponseHeaders(404, 0);
            sendResponse(exchange, e.getMessage());
            return;
        }

        String taskJson = gson.toJson(task);
        exchange.sendResponseHeaders(200, 0);
        sendResponse(exchange, taskJson);
    }

    private void deleteSubTaskById(HttpExchange exchange, String idStr) throws IOException {
        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            exchange.sendResponseHeaders(400, 0);
            sendResponse(exchange, "Неверный формат. Id должен быть числом.");
            return;
        }

        manager.deleteSubTask(id);
        exchange.sendResponseHeaders(200, 0);
        sendResponse(exchange, String.format("Задача с id = %d удалена", id));
    }
}
