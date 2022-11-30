package net.sourceforge.ganttproject.task;
import net.sourceforge.ganttproject.task.Tag;
import net.sourceforge.ganttproject.task.Task;

import java.util.Iterator;

public interface TagManager {
    public String getTagName();

    public Iterator<Tag> getTags();

    public Tag getTag(String tagName);

    public Iterator<Task> getTasksByTag(Tag tag);

    public int getNumberOfTags();
}