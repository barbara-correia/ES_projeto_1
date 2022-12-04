package net.sourceforge.ganttproject.action.favorites;

import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.action.GPAction;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.task.FavoritesManager;
import net.sourceforge.ganttproject.task.TaskManager;

import java.awt.event.ActionEvent;

public class SelectFavAction extends GPAction{
    private final IGanttProject myProject;
    private final UIFacade myUiFacade;

    private final TaskManager myTaskManager;

    private static final String COPY = "Copiar";

    private final FavoritesManager myFavoriteManager;


    public SelectFavAction(IGanttProject project, UIFacade uiFacade,TaskManager myTaskManager,FavoritesManager myFavoriteManager) {
        super(COPY);
        myProject = project;
        myUiFacade = uiFacade;
        this.myTaskManager = myTaskManager;
        this.myFavoriteManager = myFavoriteManager;

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        SelectFavDialog sf = new SelectFavDialog(myUiFacade,myProject, myTaskManager, myFavoriteManager);
        sf.setVisible(true);
    }
}