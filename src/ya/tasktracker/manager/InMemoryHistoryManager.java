package ya.tasktracker.manager;

import ya.tasktracker.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> taskList;
    private Node head;
    private Node lastNode;

    public InMemoryHistoryManager() {
        taskList = new HashMap<>();
    }


    @Override
    public void add(Task task) {
        if(taskList.containsKey(task.getId())){
            removeNode(taskList.get(task.getId()));
        }
        if(head == null){
            head = new Node(task);
            lastNode = head;
        }else {
            lastNode.setNext(new Node(task, lastNode));
            lastNode = lastNode.getNext();
        }
        taskList.put(task.getId(),lastNode);
    }

    @Override
    public void remove(int id) {
        Node node = taskList.get(id);
        removeNode(node);
    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> list = new ArrayList<>();
        Node node = head;
        while (node != null){
            list.add(node.getTask());
            node = node.getNext();
        }

        return list;
    }

    private void removeNode(Node node){
        node.getPrev().setNext(node.getNext());
        if(node.getNext() != null){
            node.getNext().setPrev(node.getPrev());
        }
        taskList.remove(node.getTask().getId());
    }
}
