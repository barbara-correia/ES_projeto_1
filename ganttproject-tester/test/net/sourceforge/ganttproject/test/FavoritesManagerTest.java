package net.sourceforge.ganttproject.test;

import biz.ganttproject.core.time.GanttCalendar;
import net.sourceforge.ganttproject.GanttTask;
import net.sourceforge.ganttproject.TestSetupHelper;
import net.sourceforge.ganttproject.task.FavoritesManager;
import net.sourceforge.ganttproject.task.FavoritesManagerImpl;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskManagerImpl;
import org.junit.Test;

public class FavoritesManagerTest {

    @Test
    public void addFavorites(){

        GanttCalendar calendar = TestSetupHelper.newMonday();
        TaskManagerImpl manager = (TaskManagerImpl) TestSetupHelper.newTaskManagerBuilder().build();

        FavoritesManager fm = new FavoritesManagerImpl();

        Task z = new GanttTask("abc",calendar,10L,manager,-1);
        Task w = new GanttTask("123",calendar,10L,manager,-1);
        Task y = new GanttTask("ES",calendar,10L,manager,-1);

        fm.addFavorite(z);
        fm.addFavorite(w);

        assert(fm.isFavorite(z));
        assert(fm.isFavorite(w));
        assert(!fm.isFavorite(y));
    }

    @Test
    public void removeFavorites(){

        GanttCalendar calendar = TestSetupHelper.newMonday();
        TaskManagerImpl manager = (TaskManagerImpl) TestSetupHelper.newTaskManagerBuilder().build();

        FavoritesManager fm = new FavoritesManagerImpl();

        Task a = new GanttTask("review",calendar,10L,manager,-1);
        Task b = new GanttTask("play",calendar,10L,manager,-1);
        Task c = new GanttTask("eat",calendar,10L,manager,-1);
        Task d = new GanttTask("code",calendar,10L,manager,-1);
        Task e = new GanttTask("redo",calendar,10L,manager,-1);
        Task f = new GanttTask("todo",calendar,10L,manager,-1);

        fm.addFavorite(a);
        fm.addFavorite(b);
        fm.addFavorite(c);
        fm.addFavorite(d);
        fm.addFavorite(e);

        assert(fm.isFavorite(a));
        fm.removeFavorite(a);
        assert(!fm.isFavorite(a));

        assert(!fm.isFavorite(e));
        fm.removeFavorite(e);
        assert(!fm.isFavorite(e));

        fm.removeFavorite(b);
        fm.removeFavorite(c);
        fm.removeFavorite(d);

        assert(fm.isFavorite(b));
        assert(fm.isFavorite(c));
        assert(fm.isFavorite(d));
    }

}
