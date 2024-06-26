package ya.tasktracker.manager;

import ya.tasktracker.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class InMemoryHistoryManager implements HistoryManager {
    protected final Map<Integer, Node> taskList;
    private Node head;
    private Node lastNode;

    public InMemoryHistoryManager() {
        taskList = new HashMap<>();
    }


    @Override
    public void add(Task task) {
        if (taskList.containsKey(task.getId())) {
            removeNode(taskList.get(task.getId()));
        }
        if (head == null) {
            head = new Node(task);
            lastNode = head;
        } else {
            lastNode.setNext(new Node(task, lastNode));
            lastNode = lastNode.getNext();
        }
        taskList.put(task.getId(), lastNode);
    }

    @Override
    public void remove(int id) {
        Node node = taskList.get(id);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
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

        taskList.remove(node.getTask().getId());

    }
}
