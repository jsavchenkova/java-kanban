package ya.tasktracker.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import ya.tasktracker.manager.TaskManager;
import ya.tasktracker.util.DurationAdapter;
import ya.tasktracker.util.ZonedDateTimeAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.time.Duration;
import java.time.ZonedDateTime;

public abstract class BaseHandler {
    TaskManager manager;
    Gson gson;

    public BaseHandler(TaskManager taskManager) {
        manager = taskManager;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .excludeFieldsWithoutExposeAnnotation();
        gson = gsonBuilder.create();
    }

    void sendResponse(HttpExchange exchange, String text) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(text.getBytes());
        }
    }

    String[] getPath(HttpExchange exchange) {
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();
        String[] str = path.split("/");
        return str;
    }
}
