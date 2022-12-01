package net.sourceforge.ganttproject.gui.tags;


import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.task.TagManager;

import java.awt.event.ActionEvent;

/**
 * This class constructs the tab that, when clicked, opens the window created by the class TagNewDialog.
 */
public class TagNewAction extends TagAction{

    private static final String NEW_TAG = "Nova etiqueta";
    private final UIFacade myUIFacade;

    private final TagManager myTagManager;

    public TagNewAction(TagManager myTagManager,UIFacade myUIFacade){
        super(NEW_TAG);
        this.myUIFacade = myUIFacade;
        this.myTagManager = myTagManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    TagNewDialog td = new TagNewDialog(myUIFacade,myTagManager);
        td.setVisible(true);
    }
}
