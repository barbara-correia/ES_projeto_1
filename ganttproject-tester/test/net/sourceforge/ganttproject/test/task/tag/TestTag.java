package net.sourceforge.ganttproject.test.task.tag;

import biz.ganttproject.core.time.GanttCalendar;
import net.sourceforge.ganttproject.GanttTask;
import net.sourceforge.ganttproject.task.*;
import net.sourceforge.ganttproject.TestSetupHelper;
import org.junit.Test;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TestTag {
    @Test
    public void addTasks(){
        Tag a = new TagImpl("a", Color.RED);
        Tag b = new TagImpl("b", Color.BLUE);

        GanttCalendar calendar = TestSetupHelper.newMonday();
        TaskManagerImpl manager = (TaskManagerImpl) TestSetupHelper.newTaskManagerBuilder().build();
        Task z = new GanttTask("z",calendar,10L,manager,-1);
        Task w = new GanttTask("z",calendar,10L,manager,-1);
        Task y = new GanttTask("z",calendar,10L,manager,-1);

        a.addTagToTask(z);
        b.addTagToTask(w);
        b.addTagToTask(y);

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
        Tag a = new TagImpl("a", Color.RED);
        Tag b = new TagImpl("b", Color.BLUE);

        GanttCalendar calendar = TestSetupHelper.newMonday();
        TaskManagerImpl manager = (TaskManagerImpl) TestSetupHelper.newTaskManagerBuilder().build();
        Task z = new GanttTask("z",calendar,10L,manager,-1);
        Task w = new GanttTask("z",calendar,10L,manager,-1);
        Task y = new GanttTask("z",calendar,10L,manager,-1);//
        Task u = new GanttTask("z",calendar,10L,manager,-1);
        Task v = new GanttTask("z",calendar,10L,manager,-1);
        Task o = new GanttTask("z",calendar,10L,manager,-1);

        a.addTagToTask(z);
        a.addTagToTask(u);
        a.addTagToTask(v);
        b.addTagToTask(w);
        b.addTagToTask(y);
        b.addTagToTask(o);

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

    @Test
    public void testTagManager(){
        Tag a = new TagImpl("a", Color.BLUE);
        Tag b = new TagImpl("b", Color.RED);


        GanttCalendar calendar = TestSetupHelper.newMonday();
        TaskManagerImpl manager = (TaskManagerImpl) TestSetupHelper.newTaskManagerBuilder().build();

        TagManager tManager = new TagManagerImpl();
        assert(tManager.getNumberOfTags() == 0);

        Task z = new GanttTask("z",calendar,10L,manager,-1);
        Task w = new GanttTask("z",calendar,10L,manager,-1);
        Task y = new GanttTask("z",calendar,10L,manager,-1);//
        Task u = new GanttTask("z",calendar,10L,manager,-1);
        Task v = new GanttTask("z",calendar,10L,manager,-1);
        Task o = new GanttTask("z",calendar,10L,manager,-1);

        tManager.addTag(a);

        assert(tManager.getNumberOfTags() == 1);

        tManager.removeTag(a.getTagName());

        assert (tManager.getNumberOfTags() == 0);

        tManager.addTag(a);
        tManager.addTag(b);

        assert (tManager.getNumberOfTags() == 2);

        tManager.addTaskToTag(a.getTagName(), z);

        Iterator<Task> itA = a.getTaggedTasks();
        List<Task> testA = new LinkedList<>();
        while(itA.hasNext()) {
            Task t = itA.next();
            testA.add(t);
        }

        assert(a.getNumberOfTaggedTasks() == 1);
        assert(testA.get(0).equals(z));

        tManager.addTaskToTag(a.getTagName(), w);
        assert (a.getNumberOfTaggedTasks() == 2);

        a.removeTaskFromTag(w);
        a.removeTaskFromTag(z);

        assert(a.getNumberOfTaggedTasks() == 0);


        tManager.addTaskToTag(b.getTagName(), y);

        Iterator<Task> it2 = tManager.getTasksByTag(b);
        int i = 0;
        while(it2.hasNext()){
            i++;
            it2.next();
        }
        assert(i == 1);

        tManager.addTaskToTag(b.getTagName(), u);
        it2 = tManager.getTasksByTag(b);
        i = 0;
        while(it2.hasNext()){
            i++;
            it2.next();
        }
        assert(i == 2);

        it2 = tManager.getTasksByTag(b.getTagName());
        i = 0;
        while(it2.hasNext()){
            i++;
            it2.next();
        }
        assert(i == 2);

    }

}