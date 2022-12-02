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

public class TagDeleteDialog {

    private static final String PANEL_TITLE = "Apagar etiqueta";
    private static final String ENTER_NAME = "Insira nome da etiqueta";

    private boolean change;

    private final StringOption myNameField = new DefaultStringOption("name");

    private final GPOptionGroup myGroup;


    private final UIFacade myUIFacade;

    private final TagManager myTagManager;

   /* private final GPAction mySetDefaultColorAction = new GPAction("defaultColor") {
        @Override
        public void actionPerformed(ActionEvent e) {
            tagColorOption.setValue(Color.red);
        }
    };*/

    public TagDeleteDialog(UIFacade uiFacade, TagManager tagManager) {
        myUIFacade = uiFacade;
        myTagManager = tagManager;
        myGroup = new GPOptionGroup("", new GPOption[]{myNameField});
        myGroup.setTitled(false);
    }

    /**
     * This method indicates if there was any change.
     * @return true if there was any change made or false if there wasn t any change made
     */
    public boolean result() {
        return change;
    }

    /**
     * This method sets the window to visible.
     * @param isVisible - indicates if the window is to be visible or not
     */
    public void setVisible(boolean isVisible) {
        if (isVisible) {
            loadFields();
            Component contentPane = getComponent();
            OkAction okAction = new OkAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!myNameField.getValue().equals(ENTER_NAME)) {
                        /*if(myTagManager.getTag(myNameField.getValue()) != null){
                            resetTasksBeforeDeletion();
                        }*/
                        myGroup.commit();
                        okButtonActionPerformed();
                    }
                }
            };
            CancelAction cancelAction = new CancelAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    myGroup.rollback();
                    change = false;
                }
            };
            myUIFacade.createDialog(contentPane, new Action[]{okAction, cancelAction}, PANEL_TITLE).show();
        }
    }

   /* private void resetTasksBeforeDeletion(){
        Tag toDel = myTagManager.getTag(myNameField.getValue());
        Iterator<Task> it = toDel.getTaggedTasks();
        while(it.hasNext()){
            Task current = it.next();
            TaskMutator mutator = current.createMutator();
            if(current.getColor() == toDel.getTagColor()) {
                mutator.setColor(myUIFacade.getGanttChart().getTaskDefaultColorOption().getValue());
            }
            mutator.setTag(null);
            mutator.commit();
        }
    }*/

    /**
     * This method loads the name field and the field to choose the color that will be associated with the tag.
     */
    private void loadFields() {
        myNameField.setValue(ENTER_NAME);
    }

    /**
     * This method returns the dialog component built
     * @return component
     */
    private Component getComponent() {
        OptionsPageBuilder builder = new OptionsPageBuilder();
        OptionsPageBuilder.I18N i18n = new OptionsPageBuilder.I18N() {
            @Override
            public String getOptionLabel(GPOptionGroup group, GPOption<?> option) {
                return getValue(option.getID());
            }
        };
        builder.setI18N(i18n);
        final JComponent mainPage = builder.buildPlanePage(new GPOptionGroup[]{myGroup});
        mainPage.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPage.addFocusListener(new FocusAdapter() {
            boolean isFirstTime = true;

            @Override
            public void focusGained(FocusEvent e) {
                if (isFirstTime) {
                    mainPage.requestFocus();
                    isFirstTime = false;
                }
                super.focusGained(e);
            }

        });
        return mainPage;
    }

    /**
     * This method triggers the applyChanges() function when the OK button is pressed.
     */
    private void okButtonActionPerformed() {
        applyChanges();
        change = true;
    }

    /**
     * This method created a new Tag and adds it to the tag manager.
     */
    private void applyChanges() {
        myTagManager.removeTag(myNameField.getValue());
    }
}
