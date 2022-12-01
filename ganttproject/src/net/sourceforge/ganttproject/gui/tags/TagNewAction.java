package net.sourceforge.ganttproject.gui.tags;


import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.roles.RoleManager;

import java.awt.event.ActionEvent;

public class TagNewAction extends TagAction{

    private final UIFacade myUIFacade;

    private final RoleManager myRoleManager;

    public TagNewAction(RoleManager myRoleManager, UIFacade myUIFacade){
        super("Nova Tag");
        this.myRoleManager = myRoleManager;
        this.myUIFacade = myUIFacade;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    TagDialog td = new TagDialog(myUIFacade);
        td.setVisible(true);
    }
}
