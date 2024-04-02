package ya.tasktracker.task;

import ya.tasktracker.constants.TaskType;

import java.util.Objects;

public class SubTask extends Task {
    private static final TaskType type = TaskType.SUBTASK;
    private Integer parentId;


    public SubTask(String name) {
        super(name);
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParent(Epic parent) {
        this.parentId = parent.getId();
    }

    @Override
    public String toString() {
        return "SubTask{" + super.toString() +
                "parentId=" + parentId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(parentId, subTask.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parentId);
    }

    @Override
    public String serializeToString() {
        return String.format("%s%d", super.serializeToString(), parentId);
    }
}
