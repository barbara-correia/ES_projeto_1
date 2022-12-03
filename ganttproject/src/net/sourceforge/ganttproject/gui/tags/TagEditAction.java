package net.sourceforge.ganttproject.gui.tags;

import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.task.TagManager;
import net.sourceforge.ganttproject.task.TaskManager;

import java.awt.event.ActionEvent;

/**
 * This class constructs the tab that, when clicked, opens the window created by the class TagDeleteDialog.
 */
public class TagEditAction extends TagAction{

    private static final String EDIT_TAG = "Editar etiqueta";
    private final UIFacade myUIFacade;

    private final TagManager myTagManager;

    private final TaskManager myTaskManager;
    public TagEditAction(TagManager myTagManager, UIFacade myUIFacade, TaskManager myTaskManager){
        super(EDIT_TAG);
        this.myUIFacade = myUIFacade;
        this.myTagManager = myTagManager;
        this.myTaskManager = myTaskManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TagEditDialog td = new TagEditDialog(myUIFacade,myTagManager, myTaskManager);
        td.setVisible(true);
    }
}
