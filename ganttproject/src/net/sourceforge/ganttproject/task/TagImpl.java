package net.sourceforge.ganttproject.task;


import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class TagImpl implements Tag {

    private String name;
    private Color color;
    private List<Task> taggedTasks;

    public TagImpl(String name, Color color) {
        this.name = name;
        this.taggedTasks = new LinkedList<Task>();
        this.color = color;
    }

    public String getTagName() {
        return name;
    }

    @Override
    public Color getTagColor() {
        return this.color;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public boolean addTagToTask(Task task) {
        if(taggedTasks.contains(task))
            return false;
        return taggedTasks.add(task);
    }

    public boolean removeTaskFromTag(Task task) {
        return taggedTasks.remove(task);
    }

    public Iterator<Task> getTaggedTasks() {
        Collections.sort(taggedTasks,new TaskPrioritySorting());
        return taggedTasks.iterator();
    }

    public int getNumberOfTaggedTasks() {
        return taggedTasks.size();
    }

    static class TaskPrioritySorting implements  Comparator<Task>{

        @Override
        public int compare(Task t1, Task t2) {
            return t2.getPriority().ordinal() - t1.getPriority().ordinal();
        }
    }
}