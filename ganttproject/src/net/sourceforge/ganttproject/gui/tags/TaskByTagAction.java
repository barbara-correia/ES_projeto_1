package net.sourceforge.ganttproject.gui.tags;

import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.task.TagManager;

import java.awt.event.ActionEvent;

public class TaskByTagAction extends  TagAction{

    private static final String TASK_BY_TAG = "Tarefas por etiqueta";
    private final UIFacade myUIFacade;

    private final TagManager myTagManager;

    public TaskByTagAction(TagManager myTagManager,UIFacade myUIFacade){
        super(TASK_BY_TAG);
        this.myUIFacade = myUIFacade;
        this.myTagManager = myTagManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TaskByTagDialog td = new TaskByTagDialog(myUIFacade,myTagManager);
        td.setVisible(true);
    }
}
