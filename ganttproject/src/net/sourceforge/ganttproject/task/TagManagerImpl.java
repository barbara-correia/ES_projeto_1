package net.sourceforge.ganttproject.task;
import java.util.*;

public class TagManagerImpl implements TagManager{

    private Map<String,Tag> allTags;

    public TagManagerImpl() {
        allTags = new HashMap<String,Tag>();
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
        return t != null;
    }
}