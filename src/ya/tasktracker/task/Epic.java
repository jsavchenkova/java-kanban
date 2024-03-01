package ya.tasktracker.task;

import java.util.*;

public class Epic extends Task{
//    private final Map<Integer, SubTask> subTaskHashMap;
    private final List<Integer> subTaskList;
    public Epic(String name) {
        super(name);
        subTaskList = new ArrayList<>();
//        subTaskHashMap = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", description=" + getDescription() +
                ", subTasks=" + subTaskList +
                ", status=" + getStatus() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskList, epic.subTaskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskList);
    }



//    public TaskStatus getStatus(){
//        TaskStatus status ;
//        int countNew = 0;
//        int countInProgress = 0;
//        int countDone = 0;
//        for(SubTask subTask: subTaskList) {
//            switch (subTask.getStatus()) {
//                case NEW:
//                    countNew++;
//                    break;
//                case IN_PROGRESS:
//                    countInProgress++;
//                    break;
//                case DONE:
//                    countDone++;
//                    break;
//            }
//        }
//            if(countInProgress>0 || (countDone>0 && countNew>0)){
//                return TaskStatus.IN_PROGRESS;
//            }else if(countDone>0 && countNew==0 && countInProgress ==0){
//                return TaskStatus.DONE;
//            }else{
//                return TaskStatus.NEW;
//            }
//
//    }

    public void addSubTask(SubTask subTask){
        subTaskList.add(subTask.getId());
        subTask.setParent(this);
    }

    public List<Integer> getSubTask(){
        return new ArrayList<>(subTaskList);
    }

    public void clearSubTasks(){
        subTaskList.clear();
    }
    public void removeSubtask(int id){
        subTaskList.remove(id);
    }
}
