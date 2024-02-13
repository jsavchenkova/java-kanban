package ya.tasktracker;

import java.util.Objects;

public class SubTask extends Task{
    private Epic parent;

    public SubTask(String name) {
        super(name);
    }
    public Epic getParent() {
        return parent;
    }

    public void setParent(Epic parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "SubTask{" + super.toString() +
                "parent=" + parent.getId() +": " + parent.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(parent, subTask.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parent);
    }
}
