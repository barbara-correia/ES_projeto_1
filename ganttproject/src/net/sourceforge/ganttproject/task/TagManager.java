package net.sourceforge.ganttproject.task;
import net.sourceforge.ganttproject.task.Tag;
import net.sourceforge.ganttproject.task.Task;

import java.util.Iterator;

public interface TagManager {
    //Returns an iterator of all saved tags
    public Iterator<Tag> getTags();

    //Returns the tag with the given name
    public Tag getTag(String tagName);

    //Returns all taks marked with the given tag. Receives a Tag
    public Iterator<Task> getTasksByTag(Tag tag);

    //Returns all tasks marked with the given tag. Receives a String
    public Iterator<Task> getTasksByTag(String tagName);

    //Returns the number of saved tags
    public int getNumberOfTags();

    //Adds a new tag to the system
    public boolean addTag(Tag tag);

    //Removes a tag from the system
    public boolean removeTag(String tagName);

    //Adds a task to the tag given the tag name and the new task name
    public boolean addTaskToTag(String tagName, Task task);

    //Removes a task from te tag given the task name and the tag name
    public boolean removeTaskFromTag(String tagName, Task task);
}