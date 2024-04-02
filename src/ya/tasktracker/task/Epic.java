package ya.tasktracker.task;

import ya.tasktracker.constants.TaskType;

import java.util.*;

public class Epic extends Task {
    private static final TaskType type = TaskType.EPIC;
    private final List<Integer> subTaskList;

    public Epic(String name) {
        super(name);
        subTaskList = new ArrayList<>();
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
        subTaskList.remove((Object)id);
    }
}
