package ya.tasktracker.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Epic extends AbstractTask{
    private final Map<Integer, SubTask> subTaskHashMap;
    public Epic(String name) {
        super(name);
        subTaskHashMap = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", description=" + getDescription() +
                ", subTasks=" + subTaskHashMap +
                ", status=" + getStatus() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskHashMap, epic.subTaskHashMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskHashMap);
    }


    public TaskStatus getStatus(){
        TaskStatus status ;
        int countNew = 0;
        int countInProgress = 0;
        int countDone = 0;
        for(SubTask subTask: subTaskHashMap.values()) {
            switch (subTask.getStatus()) {
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
            if(countInProgress>0 || (countDone>0 && countNew>0)){
                return TaskStatus.IN_PROGRESS;
            }else if(countDone>0 && countNew==0 && countInProgress ==0){
                return TaskStatus.DONE;
            }else{
                return TaskStatus.NEW;
            }

    }

    public void addSubTask(SubTask subTask){
        subTaskHashMap.put(subTask.getId(), subTask);
        subTask.setParent(this);
    }

    public ArrayList<SubTask> getSubTask(){
        return new ArrayList<>(subTaskHashMap.values());
    }

    public void clearSubTasks(){
        subTaskHashMap.clear();
    }
    public void removeSubtask(int id){
        subTaskHashMap.remove(id);
    }
}
