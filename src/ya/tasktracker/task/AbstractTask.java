package ya.tasktracker.task;

public abstract class AbstractTask implements ITask{

    private int id ;
    private String name;
    private String description;

    public AbstractTask(String name){
        this.name = name;

    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        if(description == null)return "";
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
