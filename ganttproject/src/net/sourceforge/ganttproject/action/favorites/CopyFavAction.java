/*
GanttProject is an opensource project management tool.
Copyright (C) 2011 GanttProject team

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 3
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.sourceforge.ganttproject.action.favorites;

import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.action.GPAction;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.UIUtil;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CopyFavAction extends GPAction {
    private final IGanttProject myProject;
    private final UIFacade myUiFacade;
    private  Task myTask;

    private static final String COPY = "Copiar Tarefa";

    private static final String ERROR_MSG = "Por favor selecione uma tarefa!";


    public CopyFavAction(IGanttProject project, UIFacade uiFacade, Task t) {
        this(project, uiFacade, IconSize.MENU, t);
    }

    private CopyFavAction(IGanttProject project, UIFacade uiFacade, IconSize size, Task t) {
        super(COPY);
        myProject = project;
        myUiFacade = uiFacade;
        myTask = t;

    }

    @Override
    public GPAction withIcon(IconSize size) {
        return new CopyFavAction(myProject, myUiFacade, myTask);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (calledFromAppleScreenMenu(e)) {
            return;
        }
        if (myTask == null) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, ERROR_MSG);
        } else{
        myUiFacade.getUndoManager().undoableEdit(getLocalizedDescription(), new Runnable() {
            @Override
            public void run() {
                getTaskManager().newTaskBuilder().buildFromTask(myTask);
            }
        });
    }
    }

    protected TaskManager getTaskManager() {
        return myProject.getTaskManager();
    }

    protected UIFacade getUIFacade() {
        return myUiFacade;
    }

    @Override
    public void updateAction() {
        super.updateAction();
    }

    public void setFavTask(Task toCopy){
        this.myTask = toCopy;
    }

}