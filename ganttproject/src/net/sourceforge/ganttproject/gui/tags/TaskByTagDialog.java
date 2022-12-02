package net.sourceforge.ganttproject.gui.tags;

import biz.ganttproject.core.option.*;
import net.sourceforge.ganttproject.action.CancelAction;
import net.sourceforge.ganttproject.action.OkAction;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.options.OptionsPageBuilder;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Iterator;

public class TaskByTagDialog {

    private static final String PANEL_TITLE = "Tarefas por etiqueta";
    private boolean change;
    private JTabbedPane tabbedPane;

    private final UIFacade myUIFacade;

    private final TagManager myTagManager;

   /* private final GPAction mySetDefaultColorAction = new GPAction("defaultColor") {
        @Override
        public void actionPerformed(ActionEvent e) {
            tagColorOption.setValue(Color.red);
        }
    };*/

    public TaskByTagDialog(UIFacade uiFacade, TagManager tagManager) {
        myUIFacade = uiFacade;
        myTagManager = tagManager;
    }

    public boolean result() {
        return change;
    }

    public void setVisible(boolean isVisible) {
        if (isVisible) {
            Component contentPane = getComponent();
            OkAction okAction = new OkAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    okButtonActionPerformed();
                }
            };
            myUIFacade.createDialog(contentPane, new Action[]{okAction}, PANEL_TITLE).show();
        }
    }


    private Component getComponent() {
        OptionsPageBuilder builder = new OptionsPageBuilder();
        OptionsPageBuilder.I18N i18n = new OptionsPageBuilder.I18N() {
            @Override
            public String getOptionLabel(GPOptionGroup group, GPOption<?> option) {
                return getValue(option.getID());
            }
        };
        builder.setI18N(i18n);
        tabbedPane = new JTabbedPane();
        Iterator<Tag> itTags = myTagManager.getTags();
        JPanel currentPanel = new JPanel();
        while(itTags.hasNext()){
            Tag currentTag = itTags.next();
            currentPanel = new JPanel();
            JLabel title = new JLabel(currentTag.getTagName());
            title.setForeground(currentTag.getTagColor());
            currentPanel.setLayout( new GridLayout(0,1));
            currentPanel.add(title);
            Iterator<Task> itTasks = currentTag.getTaggedTasks();
            while(itTasks.hasNext()){
                Task currentTask = itTasks.next();
                JLabel taskName = new JLabel(currentTask.getName());
                currentPanel.add(taskName);
            }
            tabbedPane.addTab(currentTag.getTagName(), null,
                    currentPanel);
        }
        final JPanel finalCurrentPanel = currentPanel;
        tabbedPane.addFocusListener(new FocusAdapter() {
            boolean isFirstTime = true;

            @Override
            public void focusGained(FocusEvent e) {
                if (isFirstTime) {
                    finalCurrentPanel.requestFocus();
                    isFirstTime = false;
                }
                super.focusGained(e);
            }

        });
        return tabbedPane;
    }

    private void okButtonActionPerformed() {
        change = true;
    }
}
