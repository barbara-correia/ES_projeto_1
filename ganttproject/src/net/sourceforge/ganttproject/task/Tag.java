package net.sourceforge.ganttproject.task;
import net.sourceforge.ganttproject.task.Task;

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
    Adds a new task to this tag
     */
    public boolean tagTask(Task task);

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
}