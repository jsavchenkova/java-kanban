package ya.tasktracker.task;

import java.util.Objects;

public class SubTask extends Task{
    private Integer parentId;


    public SubTask(String name) {
        super(name);
    }
    public Integer getParentId() {
        return parentId;
    }

    public void setParent(int parentId) {
        this.parentId = parentId;
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




}
