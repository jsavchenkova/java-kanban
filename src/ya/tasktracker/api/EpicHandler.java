package ya.tasktracker.api;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;

import javax.management.InstanceNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EpicHandler extends BaseHandler implements HttpHandler {
    public EpicHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] str = getPath(exchange);

        switch (method) {
            case "POST":
                putEpic(exchange);
                break;
            case "GET":
                if (str.length < 3) {
                    // список задач
                    getEpicList(exchange);
                } else if (str.length == 3) {
                    getEpicById(exchange, str[2]);
                } else if (str.length == 4) {
                    getEpicSubtasks(exchange, str[2]);
                }
                    break;
            case "DELETE":
                deleteEpicById(exchange, str[2]);
                break;
        }
    }

    private void putEpic(HttpExchange exchange) throws IOException {
        String request = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        if (request.isBlank()) {
            exchange.sendResponseHeaders(400, 0);
            sendResponse(exchange, "Пустые входные данные");
            return;
        }
        Epic task;
        try {
            task = gson.fromJson(request, Epic.class);
        } catch (JsonSyntaxException e) {
            exchange.sendResponseHeaders(400, 0);
            sendResponse(exchange, "Неверный формат входных данных");
            return;
        }
        if (task.getId() == null) {
            int id = manager.createEpic(task);
            exchange.sendResponseHeaders(201, 0);
            sendResponse(exchange, String.format("Задача создана. id = %d", id));
        } else {
            int id = manager.updateEpic(task);
            exchange.sendResponseHeaders(201, 0);
            sendResponse(exchange, String.format("Задача обновлена. id = %d", id));
        }
    }

    private void getEpicList(HttpExchange exchange) throws IOException {
        List<Task> taskList = manager.getEpics();
        String taskListJson = gson.toJson(taskList);
        exchange.sendResponseHeaders(200, 0);
        sendResponse(exchange, taskListJson);
    }

    private void getEpicById(HttpExchange exchange, String idStr) throws IOException {
        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            exchange.sendResponseHeaders(400, 0);
            sendResponse(exchange, "Неверный формат. Id должен быть числом.");
            return;
        }
        Epic task;
        try {
            task = manager.getEpic(id);
        }catch (InstanceNotFoundException e){
            exchange.sendResponseHeaders(404, 0);
            sendResponse(exchange, e.getMessage());
            return;
        }

        String taskJson = gson.toJson(task);
        exchange.sendResponseHeaders(200, 0);
        sendResponse(exchange, taskJson);
    }

    private void getEpicSubtasks(HttpExchange exchange, String idStr) throws IOException {
        int id = -1;
        try{
            id = Integer.parseInt(idStr);
        }catch(NumberFormatException e){
            exchange.sendResponseHeaders(400, 0);
            sendResponse(exchange, "Неверный формат. Id должен быть числом.");
            return;
        }
        Epic task;
        try {
            task = manager.getEpic(id);
        } catch(InstanceNotFoundException e){
            exchange.sendResponseHeaders(404, 0);
            sendResponse(exchange, e.getMessage());
            return;
        }

        List<Integer>subTaskIdList = manager.getSubTasksByEpic(task);
        List<SubTask> subTaskList = new ArrayList<>();

        for (Integer x : subTaskIdList) {
            try {
                subTaskList.add(manager.getSubtask(x));
            } catch (InstanceNotFoundException e) {
                exchange.sendResponseHeaders(404, 0);
                sendResponse(exchange, e.getMessage());
                return;
            }
        }

        String listJson = gson.toJson(subTaskList);
            exchange.sendResponseHeaders(200, 0);
            sendResponse(exchange, listJson);

    }

    private void deleteEpicById(HttpExchange exchange, String idStr) throws IOException {
        int id = -1;
        try{
            id = Integer.parseInt(idStr);
        }catch(NumberFormatException e){
            exchange.sendResponseHeaders(400, 0);
            sendResponse(exchange, "Неверный формат. Id должен быть числом.");
            return;
        }

        manager.deleteEpic(id);
        exchange.sendResponseHeaders(200, 0);
        sendResponse(exchange, String.format("Эпик с id = %d удалён", id));
    }

}
