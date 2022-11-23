package net.sourceforge.ganttproject.task;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TagImpl implements Tag{

    private String name;
    private List<Task> taggedTasks;

    public TagImpl(String name){
        this.name = name;
        this.taggedTasks = new LinkedList<Task>();
    }
    public String getTagName(){
        return name;
    }

    public boolean tagTask(Task task){
        return taggedTasks.add(task);
    }

    public boolean removeTaskFromTag(Task task){
        return taggedTasks.remove(task);
    }

    public Iterator<Task> getTaggedTasks(){
        return taggedTasks.iterator();
    }

    public int getNumberOfTaggedTasks(){
        return taggedTasks.size();
    }
}