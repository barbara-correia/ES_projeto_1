package net.sourceforge.ganttproject.action.favorites;


import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.task.FavoritesManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Creates the action of the favorite tasks' listing
 */
public class FavoritesAction extends AbstractAction implements Action {

    FavoritesManager myFavoritesManager;
    UIFacade uiFacade;


    public FavoritesAction(String text, FavoritesManager myFavoritesManager, UIFacade uiFacade){
        super(text);
        this.myFavoritesManager = myFavoritesManager;
        this.uiFacade = uiFacade;

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        FavoritesPanel favsPanel = new FavoritesPanel(myFavoritesManager, uiFacade);
        favsPanel.setVisible(true);
    }
}
