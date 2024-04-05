package ya.tasktracker.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class InMemoryHistoryManager implements HistoryManager {
    protected final Map<UUID, Node> taskList;
    private Node head;
    private Node lastNode;

    public InMemoryHistoryManager() {
        taskList = new HashMap<>();
    }


    @Override
    public void add(UUID task) {
        if (taskList.containsKey(task)) {
            removeNode(taskList.get(task));
        }
        if (head == null) {
            head = new Node(task);
            lastNode = head;
        } else {
            lastNode.setNext(new Node(task, lastNode));
            lastNode = lastNode.getNext();
        }
        taskList.put(task, lastNode);
    }

    @Override
    public void remove(UUID id) {
        Node node = taskList.get(id);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public ArrayList<UUID> getHistory() {
        ArrayList<UUID> list = new ArrayList<>();
        Node node = head;
        while (node != null) {
            list.add(node.getTask());
            node = node.getNext();
        }

        return list;
    }

    private void removeNode(Node node) {

        if (node.getPrev() != null) {
            node.getPrev().setNext(node.getNext());
        } else {
            head = node.getNext();
        }
        if (node.getNext() != null) {
            node.getNext().setPrev(node.getPrev());
        }
        taskList.remove(node.getTask());
    }
}
