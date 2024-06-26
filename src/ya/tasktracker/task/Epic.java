package ya.tasktracker.task;

import com.google.gson.annotations.Expose;
import ya.tasktracker.constants.TaskType;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Epic extends Task {
    @Expose
    private final List<Integer> subTaskList;
    @Expose
    private ZonedDateTime endTime;

    public Epic(String name) {
        super(name);
        subTaskList = new ArrayList<>();
    }

    public Epic(String[] params) {
        super(params);
        subTaskList = new ArrayList<>();
    }

    @Override
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", description=" + getDescription() +
                ", subTasks=" + subTaskList +
                ", status=" + getStatus() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskList, epic.subTaskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskList);
    }


    public void addSubTask(SubTask subTask) {
        subTaskList.add(subTask.getId());
        subTask.setParent(this);
    }

    public List<Integer> getSubTask() {
        return new ArrayList<>(subTaskList);
    }

    public void clearSubTasks() {
        subTaskList.clear();
    }

    public void removeSubtask(int id) {
        subTaskList.remove((Object) id);
    }

    @Override
    public String serializeToString() {
        String start = "";
        String finish = "";
        String duration = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm");
        if (getStartTime() != null && getDuration() != null) {
            start = getStartTime().format(formatter);
            finish = getEndTime().format(formatter);
            duration = String.valueOf(getDuration().getSeconds());
        }

        return String.format("%s,%s,%s,%s,%s,,%s,%s,%s", getId(), TaskType.EPIC, getName(), getStatus(), getDescription(),
                start, finish, duration);
    }

    @Override
    Epic fromString(String value) {
        String[] vals = value.split(",");
        return new Epic(vals);
    }
}
