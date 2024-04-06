package ya.tasktracker.manager;

public class Node {
    private int task;
    private Node prev;
    private Node next;

    public Node(int task, Node prev, Node next) {
        this.task = task;
        this.prev = prev;
        this.next = next;
    }

    public Node(int task, Node prev) {
        this.task = task;
        this.prev = prev;
    }

    public Node(int task) {
        this.task = task;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
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
