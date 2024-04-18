package ya.tasktracker.manager;

import ya.tasktracker.constants.TaskStatus;
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
    protected final Map<LocalDateTime, List<Task>> intervals;


    public InMemoryTaskManager(HistoryManager historyManager) {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        inMemoryHistoryManager = historyManager;
        indexTask = new IndexTask();
        intervals = new HashMap<>();
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
        tasks.values().stream().mapToInt(Task::getId).forEach(inMemoryHistoryManager::remove);
        tasks.clear();
    }

    public void removeAllEpics() {
        subTasks.values().stream().mapToInt(SubTask::getId).forEach(inMemoryHistoryManager::remove);
        epics.values().stream().mapToInt(Epic::getId).forEach(inMemoryHistoryManager::remove);
        subTasks.clear();
        epics.clear();
    }

    public void removeAllSubTask() {
        for (Epic epic : epics.values()) {
            epic.clearSubTasks();
            setEpicStatus(epic);
            setEpicTimes(epic);
        }
        subTasks.values().stream().mapToInt(SubTask::getId).forEach(inMemoryHistoryManager::remove);
        subTasks.clear();
    }


    public Optional<Task> getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            inMemoryHistoryManager.add(task);
        }
        return Optional.ofNullable(tasks.get(id));
    }

    public Optional<Epic> getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            inMemoryHistoryManager.add(epic);
        }
        return Optional.ofNullable(epics.get(id));
    }

    public Optional<SubTask> getSubtask(int id) {
        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            inMemoryHistoryManager.add(subTask);
        }
        return Optional.ofNullable(subTasks.get(id));
    }

    public int createTask(Task task) {
        if (!quickCheckTimeInterval(task)) {
            return -1;
        }
        addToIntervals(task);
        int id = indexTask.getNextId();
        task.setId(id);
        tasks.put(task.getId(), task);
        return id;
    }

    public int createEpic(Epic task) {
        if (!quickCheckTimeInterval(task)) {
            return -1;
        }
        int id = indexTask.getNextId();
        task.setId(id);
        updateEpic(task);
        return id;
    }

    public int createSubTask(SubTask task) {
        if (!quickCheckTimeInterval(task)) {
            return -1;
        }
        addToIntervals(task);
        int id = indexTask.getNextId();
        task.setId(id);
        updateSubTask(task);
        return id;
    }

    public int updateTask(Task task) {
        if (!quickCheckTimeInterval(task)) {
            return -1;
        }
        addToIntervals(task);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    public void updateEpic(Epic task) {
        epics.put(task.getId(), task);

    }

    public int updateSubTask(SubTask task) {
        if (!quickCheckTimeInterval(task)) {
            return -1;
        }
        addToIntervals(task);
        subTasks.put(task.getId(), task);
        if (task.getParentId() != null) {
            setEpicStatus(epics.get(task.getParentId()));
            setEpicTimes(epics.get(task.getParentId()));
        }
        return task.getId();
    }

    public void deleteTask(int id) {
        deleteFromInterval(tasks.get(id));
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
        deleteFromInterval(subTasks.get(id));
        inMemoryHistoryManager.remove(id);
        subTasks.remove(id);
    }

    public List<Integer> getSubTasksByEpic(Epic epic) {
        return new ArrayList<>(epic.getSubTask());
    }

    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        TreeSet<Task> prioritizedList = new TreeSet<>(Comparator.comparing(Task::getStartTime));
        tasks.values().stream()
                .filter(x -> x.getStartTime() != null)
                .forEach(prioritizedList::add);

        subTasks.values().stream()
                .filter(x -> x.getStartTime() != null)
                .forEach(prioritizedList::add);
        return prioritizedList;
    }

    private void setEpicStatus(Epic epic) {
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
                .map(subTasks::get)
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
                .map(subTasks::get)
                .filter(x -> x.getStartTime() != null && x.getDuration() != null)
                .map(SubTask::getDuration)
                .mapToLong(Duration::getSeconds).sum();
        epic.setDuration(Duration.ofSeconds(sumDuration));
    }

    private void setEpicEndTime(Epic epic) {
        Optional<LocalDateTime> maxEndTime = epic.getSubTask().stream()
                .map(subTasks::get)
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

    @Override
    public boolean checkTimeInterval(Task task) {
        if (task.getStartTime() == null || task.getDuration() == null) {
            return true;
        }
        long count = getPrioritizedTasks().stream()
                .filter(x ->
                        (x.getStartTime().isAfter(task.getStartTime()) && x.getStartTime().isBefore(task.getEndTime()))
                                || (x.getEndTime().isAfter(task.getStartTime()) && x.getEndTime().isBefore(task.getEndTime())))
                .count();
        return count == 0;
    }

    @Override
    public boolean quickCheckTimeInterval(Task task) {
        if (task.getStartTime() == null || task.getDuration() == null) {
            return true;
        }
        LocalDateTime startInterval = toInterval(task.getStartTime());
        LocalDateTime endInterval = toInterval(task.getEndTime());
        while (startInterval.isBefore(endInterval) || startInterval.isEqual(endInterval)) {
            if (intervals.containsKey(startInterval)) {
                long count = intervals.get(startInterval).stream()
                        .filter(x ->
                                (x.getStartTime().isAfter(task.getStartTime()) && x.getStartTime().isBefore(task.getEndTime()))
                                        || (x.getEndTime().isAfter(task.getStartTime()) && x.getEndTime().isBefore(task.getEndTime())))
                        .count();
                if (count > 0) {
                    return false;
                }
            }
            startInterval = startInterval.plusMinutes(15);
        }
        return true;
    }

    private LocalDateTime toInterval(LocalDateTime time) {
        int minute = time.getMinute();
        if (minute >= 0 && minute < 15) {
            return LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour(), 0);
        }
        if (minute >= 15 && minute < 30) {
            return LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour(), 15);
        }
        if (minute >= 30 && minute < 45) {
            return LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour(), 30);
        }
        return LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour(), 45);
    }

    private void addToIntervals(Task task) {
        if (task.getStartTime() == null || task.getDuration() == null) {
            return;
        }
        LocalDateTime startInterval = toInterval(task.getStartTime());
        LocalDateTime endInterval = toInterval(task.getEndTime());
        while (startInterval.isBefore(endInterval) || startInterval.isEqual(endInterval)) {
            if (!intervals.containsKey(startInterval)) {
                intervals.put(startInterval, new ArrayList<>());
            }
            intervals.get(startInterval).add(task);
            startInterval = startInterval.plusMinutes(15);
        }
    }

    private void deleteFromInterval(Task task) {
        if (task.getStartTime() == null || task.getDuration() == null) {
            return;
        }
        LocalDateTime startInterval = toInterval(task.getStartTime());
        LocalDateTime endInterval = toInterval(task.getEndTime());
        while (startInterval.isBefore(endInterval) || startInterval.isEqual(endInterval)) {
            intervals.get(startInterval).remove(task);
            startInterval = startInterval.plusMinutes(15);
        }
    }
}
