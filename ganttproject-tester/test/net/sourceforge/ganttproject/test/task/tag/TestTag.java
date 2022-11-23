package net.sourceforge.ganttproject.test.task.tag;

import biz.ganttproject.core.time.GanttCalendar;
import net.sourceforge.ganttproject.GanttTask;
import net.sourceforge.ganttproject.task.*;
import net.sourceforge.ganttproject.TestSetupHelper;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TestTag {
    @Test
    public void addTasks(){
        Tag a = new TagImpl("a");
        Tag b = new TagImpl("b");

        GanttCalendar calendar = TestSetupHelper.newMonday();
        TaskManagerImpl manager = (TaskManagerImpl) TestSetupHelper.newTaskManagerBuilder().build();
        Task z = new GanttTask("z",calendar,10L,manager,-1);
        Task w = new GanttTask("z",calendar,10L,manager,-1);
        Task y = new GanttTask("z",calendar,10L,manager,-1);

        a.tagTask(z);
        b.tagTask(w);
        b.tagTask(y);

        assert(a.getNumberOfTaggedTasks() == 1);
        assert (b.getNumberOfTaggedTasks() == 2);

        Iterator<Task> itA = a.getTaggedTasks();
        List<Task> testA = new LinkedList<>();
        while(itA.hasNext()) {
            Task t = itA.next();
            testA.add(t);
        }

        assert(testA.get(0).equals(z));

        Iterator<Task> itB = b.getTaggedTasks();
        List<Task> testB = new LinkedList<>();
        while(itB.hasNext()) {
            Task f = itB.next();
            testB.add(f);
        }

        assert(testB.get(0).equals(w));
        assert(testB.get(1).equals(y));
    }

    @Test
    public void removeTasks(){
        Tag a = new TagImpl("a");
        Tag b = new TagImpl("b");

        GanttCalendar calendar = TestSetupHelper.newMonday();
        TaskManagerImpl manager = (TaskManagerImpl) TestSetupHelper.newTaskManagerBuilder().build();
        Task z = new GanttTask("z",calendar,10L,manager,-1);
        Task w = new GanttTask("z",calendar,10L,manager,-1);
        Task y = new GanttTask("z",calendar,10L,manager,-1);//
        Task u = new GanttTask("z",calendar,10L,manager,-1);
        Task v = new GanttTask("z",calendar,10L,manager,-1);
        Task o = new GanttTask("z",calendar,10L,manager,-1);

        a.tagTask(z);
        a.tagTask(u);
        a.tagTask(v);
        b.tagTask(w);
        b.tagTask(y);
        b.tagTask(o);

        assert(a.getNumberOfTaggedTasks() == 3);
        assert(b.getNumberOfTaggedTasks() == 3);

        a.removeTaskFromTag(z);

        assert (a.getNumberOfTaggedTasks() == 2);
        assert (b.getNumberOfTaggedTasks() == 3);

        b.removeTaskFromTag(w);
        b.removeTaskFromTag(y);

        assert (a.getNumberOfTaggedTasks() == 2);
        assert (b.getNumberOfTaggedTasks() == 1);

        Iterator<Task> itA = a.getTaggedTasks();
        List<Task> testA = new LinkedList<>();
        while(itA.hasNext()) {
            Task t = itA.next();
            testA.add(t);
        }

        assert(testA.get(0).equals(u));
        assert(testA.get(1).equals(v));

        Iterator<Task> itB = b.getTaggedTasks();
        List<Task> testB = new LinkedList<>();
        while(itB.hasNext()) {
            Task f = itB.next();
            testB.add(f);
        }

        assert(testB.get(0).equals(o));

    }
}