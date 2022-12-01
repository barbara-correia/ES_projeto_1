package net.sourceforge.ganttproject.gui.tags;

import javafx.event.ActionEvent;
import net.sourceforge.ganttproject.action.ActionStateChangedListener;
import net.sourceforge.ganttproject.action.GPAction;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class holds the responsibility to implement any graphic action that has to do with tags.
 */
abstract class TagAction extends GPAction {

    private final List<ActionStateChangedListener> myListeners = new ArrayList<ActionStateChangedListener>();

    public TagAction(String name) {
        super(name);
    }

    public void setEnabled(boolean newValue) {
        super.setEnabled(newValue);
        for (ActionStateChangedListener l : myListeners) {
            l.actionStateChanged();
        }
    }
}


