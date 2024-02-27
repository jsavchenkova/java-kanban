package ya.tasktracker;

public abstract class AbstractTask {

    private final int id ;
    private String name;
    private String description;

    public AbstractTask(String name){
        id = IndexTask.id++;
        this.name = name;

    }

    public int getId() {
        return id;
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
