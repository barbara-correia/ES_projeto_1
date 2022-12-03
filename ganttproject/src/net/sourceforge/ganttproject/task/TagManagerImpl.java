package net.sourceforge.ganttproject.task;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TagManagerImpl implements TagManager{

    private Map<String,Tag> allTags;

    private Map<Integer,Tag> tagsById;

    private int count;

    public TagManagerImpl() {
        allTags = new HashMap<String,Tag>();
        tagsById = new HashMap<Integer,Tag>();
        this.count = 0;
    }


    public Iterator<Tag> getTags() {
        List<Tag> res = new LinkedList<Tag>();
        Iterator<String> it = allTags.keySet().iterator();
        while(it.hasNext()){
            res.add(allTags.get(it.next()));
        }
        return res.iterator();
    }

    public Tag getTag(String tagName) {
        return allTags.get(tagName);
    }

    public Iterator<Task> getTasksByTag(Tag tag) {
        return tag.getTaggedTasks();
    }

    public Iterator<Task> getTasksByTag(String tagName) {
        return allTags.get(tagName).getTaggedTasks();
    }

    public int getNumberOfTags() {
        return allTags.size();
    }

    public boolean addTag(Tag tag) {
        Tag t =  allTags.put(tag.getTagName(),tag);
        tagsById.put(count, tag);
        tag.setId(count);
        count++;
        return t != null;
    }

    @Override
    public boolean removeTag(String tagName) {
       Tag t = allTags.remove(tagName);
       try {
           tagsById.remove(t.getId());
           return true;
       }catch(Exception e){
           return false;
       }
    }

    public boolean addTaskToTag(int id, Task task) {
        return tagsById.get(id).addTagToTask(task);
    }
    public boolean addTaskToTag(String name, Task task){
        return allTags.get(name).addTagToTask(task);
    }

    public boolean removeTaskFromTag(int id,Task task) {
        return tagsById.get(id).removeTaskFromTag(task);
    }

    @Override
    public void editTag(String name, String newName, Color newColor) {
        Tag tag = allTags.get(name);
        tagsById.get(tag.getId()).setName(newName);
        tagsById.get(tag.getId()).setColor(newColor);

        allTags.remove(name);
        allTags.put(newName, tag);
    }
}