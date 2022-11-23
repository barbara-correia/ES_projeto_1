package net.sourceforge.ganttproject.task;
import net.sourceforge.ganttproject.task.Task;

import java.util.Iterator;

public interface Tag {
    public String getTagName();

    public boolean tagTask(Task task);

    public boolean removeTaskFromTag(Task task);

    public Iterator<Task> getTaggedTasks();

    public int getNumberOfTaggedTasks();
}