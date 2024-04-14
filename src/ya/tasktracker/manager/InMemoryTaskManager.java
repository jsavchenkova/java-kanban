package ya.tasktracker.manager;

import ya.tasktracker.task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Task> tasks;
    protected final Map<Integer, Epic> epics;
    protected final Map<Integer, SubTask> subTasks;
    private final HistoryManager inMemoryHistoryManager;
    protected final IndexTask indexTask;


    public InMemoryTaskManager(HistoryManager historyManager) {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        inMemoryHistoryManager = historyManager;
        indexTask = new IndexTask();
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
            setEpicTimes(epic);
        }
        for (SubTask st : subTasks.values()) {
            inMemoryHistoryManager.remove(st.getId());
        }
        subTasks.clear();
    }


    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            inMemoryHistoryManager.add(task);
        }
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            inMemoryHistoryManager.add(epic);
        }
        return epics.get(id);
    }

    public SubTask getSubtask(int id) {
        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            inMemoryHistoryManager.add(subTask);
        }
        return subTasks.get(id);
    }

    public int createTask(Task task) {
        int id = indexTask.getNextId();
        task.setId(id);
        tasks.put(task.getId(), task);
        return id;
    }

    public int createEpic(Epic task) {
        int id = indexTask.getNextId();
        task.setId(id);
        updateEpic(task);
        return id;
    }

    public int createSubTask(SubTask task) {
        int id = indexTask.getNextId();
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
            setEpicTimes(epics.get(task.getParentId()));
        }
    }

    public void deleteTask(int id) {
        inMemoryHistoryManager.remove(id);
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        Epic epic = epics.get(id);
        for (int taskId : epic.getSubTask()) {
            inMemoryHistoryManager.remove(taskId);
            deleteSubTask(taskId);
        }
        inMemoryHistoryManager.remove(id);
        epics.remove(id);
    }

    public void deleteSubTask(int id) {
        SubTask subTask = subTasks.get(id);
        epics.get(subTask.getParentId()).removeSubtask(subTask.getId());
        setEpicStatus(epics.get(subTask.getParentId()));
        setEpicTimes(epics.get(subTask.getParentId()));
        inMemoryHistoryManager.remove(id);
        subTasks.remove(id);
    }

    public List<Integer> getSubTasksByEpic(Epic epic) {
        return new ArrayList<>(epic.getSubTask());
    }

    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    private void setEpicStatus(Epic epic) {
        TaskStatus status;
        int countNew = 0;
        int countInProgress = 0;
        int countDone = 0;
        for (int subTaskId : epic.getSubTask()) {
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

    private void setEpicStartTime(Epic epic) {
        Optional<LocalDateTime> minStartTime = epic.getSubTask().stream()
                .map(x -> subTasks.get(x))
                .filter(x -> x.getStartTime() != null && x.getDuration() != null)
                .map(SubTask::getStartTime)
                .min(LocalDateTime::compareTo);
        if (minStartTime.isPresent()) {
            epic.setStartTime(minStartTime.get());
        } else {
            epic.setStartTime(null);
        }
    }

    private void setEpicDuration(Epic epic) {
        long sumDuration = epic.getSubTask().stream()
                .map(x -> subTasks.get(x))
                .filter(x -> x.getStartTime() != null && x.getDuration() != null)
                .map(SubTask::getDuration)
                .mapToLong(Duration::getSeconds).sum();
        epic.setDuration(Duration.ofSeconds(sumDuration));
    }

    private void setEpicEndTime(Epic epic) {
        Optional<LocalDateTime> maxEndTime = epic.getSubTask().stream()
                .map(x -> subTasks.get(x))
                .filter(x -> x.getStartTime() != null && x.getDuration() != null)
                .map(SubTask::getEndTime)
                .max(LocalDateTime::compareTo);
        if (maxEndTime.isPresent()) {
            epic.setEndTime(maxEndTime.get());
        } else {
            epic.setEndTime(null);
        }
    }

    private void setEpicTimes(Epic epic) {
        setEpicStartTime(epic);
        setEpicDuration(epic);
        setEpicEndTime(epic);
    }

}
