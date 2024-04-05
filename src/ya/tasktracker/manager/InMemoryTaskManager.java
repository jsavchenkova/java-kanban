package ya.tasktracker.manager;

import ya.tasktracker.task.*;

import java.util.*;

class InMemoryTaskManager implements TaskManager {
    protected final Map<UUID, Task> tasks;
    protected final Map<UUID, Epic> epics;
    protected final Map<UUID, SubTask> subTasks;
    private final HistoryManager inMemoryHistoryManager;


    public InMemoryTaskManager(HistoryManager historyManager) {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        inMemoryHistoryManager = historyManager;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Task> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Task> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void removeAllTasks() {
        for (Task t : tasks.values()) {
            inMemoryHistoryManager.remove(t.getId());
        }
        tasks.clear();
    }

    public void removeAllEpics() {
        for (SubTask st : subTasks.values()) {
            inMemoryHistoryManager.remove(st.getId());
        }
        for (Epic e : epics.values()) {
            inMemoryHistoryManager.remove(e.getId());
        }
        subTasks.clear();
        epics.clear();
    }

    public void removeAllSubTask() {
        for (Epic epic : epics.values()) {
            epic.clearSubTasks();
            setEpicStatus(epic);
        }
        for (SubTask st : subTasks.values()) {
            inMemoryHistoryManager.remove(st.getId());
        }
        subTasks.clear();
    }


    public Task getTask(UUID id) {
        Task task = tasks.get(id);
        if (task != null) {
            inMemoryHistoryManager.add(id);
        }
        return tasks.get(id);
    }

    public Epic getEpic(UUID id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            inMemoryHistoryManager.add(id);
        }
        return epics.get(id);
    }

    public SubTask getSubtask(UUID id) {
        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            inMemoryHistoryManager.add(id);
        }
        return subTasks.get(id);
    }

    public UUID createTask(Task task) {
        UUID id = UUID.randomUUID();
        task.setId(id);
        tasks.put(task.getId(), task);
        return id;
    }

    public UUID createEpic(Epic task) {
        UUID id = UUID.randomUUID();
        task.setId(id);
        updateEpic(task);
        return id;
    }

    public UUID createSubTask(SubTask task) {
        UUID id = UUID.randomUUID();
        task.setId(id);
        updateSubTask(task);
        return id;
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic task) {
        epics.put(task.getId(), task);

    }

    public void updateSubTask(SubTask task) {
        subTasks.put(task.getId(), task);
        if (task.getParentId() != null) {
            setEpicStatus(epics.get(task.getParentId()));
        }
    }

    public void deleteTask(UUID id) {
        inMemoryHistoryManager.remove(id);
        tasks.remove(id);
    }

    public void deleteEpic(UUID id) {
        Epic epic = epics.get(id);
        for (UUID taskId : epic.getSubTask()) {
            inMemoryHistoryManager.remove(taskId);
            deleteSubTask(taskId);
        }
        inMemoryHistoryManager.remove(id);
        epics.remove(id);
    }

    public void deleteSubTask(UUID id) {
        SubTask subTask = subTasks.get(id);
        epics.get(subTask.getParentId()).removeSubtask(subTask.getId());
        setEpicStatus(epics.get(subTask.getParentId()));
        inMemoryHistoryManager.remove(id);
        subTasks.remove(id);
    }

    public List<UUID> getSubTasksByEpic(Epic epic) {
        return new ArrayList<>(epic.getSubTask());
    }

    public List<UUID> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    private void setEpicStatus(Epic epic) {
        TaskStatus status;
        int countNew = 0;
        int countInProgress = 0;
        int countDone = 0;
        for (UUID subTaskId : epic.getSubTask()) {
            switch (subTasks.get(subTaskId).getStatus()) {
                case NEW:
                    countNew++;
                    break;
                case IN_PROGRESS:
                    countInProgress++;
                    break;
                case DONE:
                    countDone++;
                    break;
            }
        }
        if (countInProgress > 0 || (countDone > 0 && countNew > 0)) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        } else if (countDone > 0 && countNew == 0 && countInProgress == 0) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
    }

}
