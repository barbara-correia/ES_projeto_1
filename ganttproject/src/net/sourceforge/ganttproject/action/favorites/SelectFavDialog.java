package net.sourceforge.ganttproject.action.favorites;

import biz.ganttproject.core.option.*;
import net.sourceforge.ganttproject.GPLogger;
import net.sourceforge.ganttproject.IGanttProject;
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

public class SelectFavDialog {
    private static final String PANEL_TITLE = "Editar etiqueta";

    private boolean change;

    private final UIFacade myUIFacade;

    private final IGanttProject myProject;

    private final TaskManager myTaskManager;

    private final JComboBox favsComboBox = new JComboBox();

    private final FavoritesManager myFavoriteManager;

    private Task current;


    public SelectFavDialog(UIFacade uiFacade, IGanttProject myProject, TaskManager myTaskManager, FavoritesManager myFavoriteManager) {
        myUIFacade = uiFacade;
        this.myProject = myProject;
        this.myTaskManager = myTaskManager;
        this.myFavoriteManager = myFavoriteManager;
        current = null;
    }


   /*   This method indicates if there was any change.
      @return true if there was any change made or false if there wasn t any change made*/

    public boolean result() {
        return change;
    }


    /*This method sets the window to visible.
    @param isVisible - indicates if the window is to be visible or not*/

    public void setVisible(boolean isVisible) {
        if (isVisible) {
            loadFields();
            Component contentPane = getComponent();
            OkAction okAction = new OkAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final GanttLanguage language = GanttLanguage.getInstance();
                    myUIFacade.getUndoManager().undoableEdit(language.getText("properties.changed"), new Runnable() {
                        @Override
                        public void run() {

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

                    okButtonActionPerformed();
                }
            };
            CancelAction cancelAction = new CancelAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    change = false;
                }
            };
            CopyFavAction copyFavAction = new CopyFavAction(myProject,myUIFacade, myFavoriteManager.getFavoriteTask((Integer) favsComboBox.getSelectedItem()));
            myUIFacade.createDialog(contentPane, new Action[]{okAction,copyFavAction, cancelAction}, PANEL_TITLE).show();
        }
    }


    /* This method loads the name field and the field to choose the color that will be associated with the tag.*/

    private void loadFields() {
        favsComboBox.setSelectedItem(-1);
    }


      /*This method returns the dialog component built
      @return component*/

    private Component getComponent() {
        OptionsPageBuilder builder = new OptionsPageBuilder();
        OptionsPageBuilder.I18N i18n = new OptionsPageBuilder.I18N() {
            @Override
            public String getOptionLabel(GPOptionGroup group, GPOption option) {
                return getValue(option.getID());
            }
        };
        builder.setI18N(i18n);
        final JComponent mainPage = new JPanel();
        mainPage.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Iterator<Task> it = myFavoriteManager.favoritesIDIterator();
            while(it.hasNext()){
                favsComboBox.addItem(it.next().getTaskID());
            }
        mainPage.add(favsComboBox);


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


    /*This method triggers the applyChanges() function when the OK button is pressed.*/

    private void okButtonActionPerformed() {
        change = true;
    }


    /* This method created a new Tag and adds it to the tag manager.*/

}