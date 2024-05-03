package ya.tasktracker.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ya.tasktracker.constants.Formatters;

import java.io.IOException;
import java.time.ZonedDateTime;

public class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {
    @Override
    public void write(JsonWriter jsonWriter, ZonedDateTime zonedDateTime) throws IOException {
        if (zonedDateTime != null) {
            jsonWriter.value(zonedDateTime.format(Formatters.dateTimeFormatter));
        } else {
            jsonWriter.value("");
        }
    }

    @Override
    public ZonedDateTime read(JsonReader jsonReader) throws IOException {
        String str = jsonReader.nextString();
        if (str.isBlank()) return null;
        return ZonedDateTime.parse(str, Formatters.dateTimeFormatter);
    }
}
