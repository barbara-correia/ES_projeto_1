package net.sourceforge.ganttproject.gui.tags;


import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.roles.RoleManager;
import net.sourceforge.ganttproject.task.TagManager;

import java.awt.event.ActionEvent;

public class TagNewAction extends TagAction{

    private final UIFacade myUIFacade;

    private final TagManager myTagManager;

    public TagNewAction(TagManager myTagManager,UIFacade myUIFacade){
        super("Nova Tag");
        this.myUIFacade = myUIFacade;
        this.myTagManager = myTagManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    TagDialog td = new TagDialog(myUIFacade,myTagManager);
        td.setVisible(true);
    }
}
