package net.sourceforge.ganttproject.action.favorites;


import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.task.FavoritesManager;
import net.sourceforge.ganttproject.task.TaskManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Creates the action of the favorite tasks' listing
 */
public class FavoritesAction extends AbstractAction implements Action {

    FavoritesManager myFavoritesManager;
    UIFacade uiFacade;

    IGanttProject myProject;

    TaskManager myTaskManager;


    public FavoritesAction(String text, FavoritesManager myFavoritesManager, UIFacade uiFacade, IGanttProject myProject, TaskManager taskManager){
        super(text);
        this.myFavoritesManager = myFavoritesManager;
        this.uiFacade = uiFacade;
        this.myProject = myProject;
        this.myTaskManager = taskManager;

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        FavoritesPanel favsPanel = new FavoritesPanel(myFavoritesManager, uiFacade, myProject, myTaskManager);
        favsPanel.setVisible(true);
    }
}
