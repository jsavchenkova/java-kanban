import com.google.gson.*;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;
import ya.tasktracker.api.HttpTaskServer;
import ya.tasktracker.manager.Managers;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.task.Epic;
import ya.tasktracker.task.SubTask;
import ya.tasktracker.task.Task;
import ya.tasktracker.util.TaskListTypeToken;

import javax.management.InstanceNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpTaskServerTest {
    HttpServer httpServer;
    TaskManager taskManager;
    static Gson gson;

    @BeforeAll
    public static void allInit() {
        gson = new GsonBuilder()
                .create();
    }

    @BeforeEach
    public void init() throws IOException {
        Managers managers = new Managers();
        taskManager = managers.getDefault();
        httpServer = HttpTaskServer.createServer(taskManager);
        httpServer.start();
    }

    @AfterEach
    public void end() {
        httpServer.stop(0);
    }

    @Test
    public void getTasksTest() throws IOException, InterruptedException {
        Task task = new Task("task1");
        Task task2 = new Task("task2");
        taskManager.createTask(task);
        taskManager.createTask(task2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        Gson gson = new GsonBuilder()
                .create();

        HttpResponse<String> response = client.send(request, handler);
        JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        Assertions.assertEquals(2, jsonArray.size());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void getTaskById() throws IOException, InterruptedException {
        Task task = new Task("task1");
        Task task2 = new Task("task2");
        taskManager.createTask(task);
        taskManager.createTask(task2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(request, handler);
        JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonObject object = jsonElement.getAsJsonObject();
        int id = object.get("id").getAsInt();

        Assertions.assertEquals(task2.getId(), id);
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void postSubtask() throws IOException, InterruptedException, InstanceNotFoundException {
        SubTask subTask = new SubTask("subtask");
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subTask)))
                .uri(url)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(request, handler);

        Assertions.assertEquals(subTask, taskManager.getSubtask(0));
        Assertions.assertEquals(201, response.statusCode());
    }

    @Test
    public void deleteEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("epic");
        SubTask subTask1 = new SubTask("subTask1");
        SubTask subTask2 = new SubTask("subTask2");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/0");
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(url)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(request, handler);

        Assertions.assertEquals(0, taskManager.getEpics().size());
        Assertions.assertEquals(0, taskManager.getSubTasks().size());
        Assertions.assertEquals(200, response.statusCode());
    }
}
