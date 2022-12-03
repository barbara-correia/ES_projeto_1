package net.sourceforge.ganttproject.gui.tags;

import biz.ganttproject.core.option.*;
import net.sourceforge.ganttproject.GPLogger;
import net.sourceforge.ganttproject.action.CancelAction;
import net.sourceforge.ganttproject.action.OkAction;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.options.OptionsPageBuilder;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.*;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Iterator;

public class TagEditDialog {

    private static final String PANEL_TITLE = "Editar etiqueta";
    private static final String ENTER_NAME = "Insira nome da etiqueta";
    private static  final String ENTER_NEW_NAME = "Insira o novo nome";

    private static final String TAG_COLOR = "Nova cor da etiqueta";

    private boolean change;

    private final StringOption myNameField = new DefaultStringOption("Nome Antigo");
    private final StringOption myNewNameField = new DefaultStringOption("Nome Novo");
    private ColorOption tagColorOption = new DefaultColorOption(TAG_COLOR);

    private final GPOptionGroup myGroup;


    private final UIFacade myUIFacade;

    private final TagManager myTagManager;

    private final TaskManager myTaskManager;

   /* private final GPAction mySetDefaultColorAction = new GPAction("defaultColor") {
        @Override
        public void actionPerformed(ActionEvent e) {
            tagColorOption.setValue(Color.red);
        }
    };*/

    public TagEditDialog(UIFacade uiFacade, TagManager tagManager, TaskManager taskManager) {
        myUIFacade = uiFacade;
        myTagManager = tagManager;
        myTaskManager = taskManager;
        myGroup = new GPOptionGroup("", new GPOption[]{myNameField, myNewNameField});
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
                        final GanttLanguage language = GanttLanguage.getInstance();
                        myUIFacade.getUndoManager().undoableEdit(language.getText("properties.changed"), new Runnable() {
                            @Override
                            public void run() {
                                changeColorAfterEdit();
                                try {
                                    myTaskManager.getAlgorithmCollection().getRecalculateTaskScheduleAlgorithm().run();
                                } catch (TaskDependencyException e) {
                                    if (!GPLogger.log(e)) {
                                            e.printStackTrace();
                                    }
                                }
                                myUIFacade.refresh();
                            }
                        });
                    }
                    myGroup.commit();
                    okButtonActionPerformed();
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

    private void changeColorAfterEdit(){
        Tag edited = myTagManager.getTag(myNameField.getValue());
        Iterator<Task> it = edited.getTaggedTasks();
        while(it.hasNext()){
            Task current = it.next();
            Tag t = current.getTag();
            TaskMutator mutator = current.createMutator();
            mutator.setTag(t);
            mutator.setColor(tagColorOption.getValue());
            mutator.commit();
        }
    }

    /**
     * This method loads the name field and the field to choose the color that will be associated with the tag.
     */
    private void loadFields() {
        myNameField.setValue(ENTER_NAME);
        myNewNameField.setValue(ENTER_NEW_NAME);
        tagColorOption.setValue(Color.red);
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

        builder.setUiFacade(myUIFacade);
        JPanel colorBox = new JPanel(new BorderLayout(5, 0));
        colorBox.add(new JLabel(TAG_COLOR, JLabel.LEFT));
        colorBox.add(builder.createColorComponent(tagColorOption).getJComponent(), BorderLayout.EAST);
        mainPage.add(colorBox);

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
        if(myNewNameField.getValue().equals(ENTER_NEW_NAME)){
            myTagManager.editTag(myNameField.getValue(), myNameField.getValue(), tagColorOption.getValue());
        }else {
            myTagManager.editTag(myNameField.getValue(), myNewNameField.getValue(), tagColorOption.getValue());
        }
    }
}
