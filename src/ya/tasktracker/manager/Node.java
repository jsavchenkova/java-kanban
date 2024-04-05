package ya.tasktracker.manager;

import java.util.UUID;

public class Node {
    private UUID task;
    private Node prev;
    private Node next;

    public Node(UUID task, Node prev, Node next) {
        this.task = task;
        this.prev = prev;
        this.next = next;
    }

    public Node(UUID task, Node prev) {
        this.task = task;
        this.prev = prev;
    }

    public Node(UUID task) {
        this.task = task;
    }

    public UUID getTask() {
        return task;
    }

    public void setTask(UUID task) {
        this.task = task;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


}
