package ya.tasktracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task{
    private final HashMap<Integer, SubTask> subTaskHashMap;
    public Epic(String name) {
        super(name);
        subTaskHashMap = new HashMap<>();
    }

    @Override
    public void setStatus(TaskStatus status){}

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

    @Override
    public TaskStatus getStatus(){
        int countNew = 0;
        int countInProgress = 0;
        int countDone = 0;
        for(SubTask subTask: subTaskHashMap.values()){
            switch (subTask.getStatus()){
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
            if(countInProgress>0 || (countDone>0 && countNew>0)){
                setStatus(TaskStatus.IN_PROGRESS);
            }else if(countDone>0 && countNew==0 && countInProgress ==0){
                setStatus(TaskStatus.DONE);
            }else{
                setStatus(TaskStatus.NEW);
            }
        }
        return super.getStatus();
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
