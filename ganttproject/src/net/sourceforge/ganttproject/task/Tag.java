package net.sourceforge.ganttproject.task;

import java.awt.*;
import java.util.Iterator;
/*
Represents a tag that allows us to filter tasks
 */
public interface Tag {
    /*
    Returns tag name
     */
    public String getTagName();

    /*
     * Changes the color of the tag
     */
    public void setColor(Color newColor);

    /*
     * Changes the name of the tag
     */
    public void setName(String newName);

    /*

     */
    public Color getTagColor();

    /*
    Adds a new task to this tag
     */
    public boolean addTagToTask(Task task);

    /*
    Removes a task from tag
     */
    public boolean removeTaskFromTag(Task task);

    /*
    Shows all tasks with this tag
     */
    public Iterator<Task> getTaggedTasks();

    /*
    Gets number of tasks with this tag
     */
    public int getNumberOfTaggedTasks();

    /*
    * Returns the id of the tag*/
    public int getId();

    /*
    Sets the id for the tag
     */
    public void setId(int id);
}