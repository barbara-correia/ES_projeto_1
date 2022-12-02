package net.sourceforge.ganttproject.gui.tags;

import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.task.TagManager;
import net.sourceforge.ganttproject.task.TaskManager;

import java.awt.event.ActionEvent;

/**
 * This class constructs the tab that, when clicked, opens the window created by the class TagDeleteDialog.
 */
public class TagDeleteAction extends TagAction{

    private static final String DEL_TAG = "Apagar etiqueta";
    private final UIFacade myUIFacade;

    private final TagManager myTagManager;
    public TagDeleteAction(TagManager myTagManager,UIFacade myUIFacade){
        super(DEL_TAG);
        this.myUIFacade = myUIFacade;
        this.myTagManager = myTagManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TagDeleteDialog td = new TagDeleteDialog(myUIFacade,myTagManager);
        td.setVisible(true);
    }
}
