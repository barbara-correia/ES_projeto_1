package net.sourceforge.ganttproject.action.favorites;

import biz.ganttproject.core.option.*;
import net.sourceforge.ganttproject.GPLogger;
import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.action.OkAction;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.options.OptionsPageBuilder;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.FavoritesManager;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * Generates the panel(popup window) with the list of favorite tasks.
 */
public class FavoritesPanel {

    private final FavoritesManager myFavoritesManager;
    private final UIFacade myUIFacade;

    private final TaskManager myTaskManager;
    private  CopyFavAction myCopyFavAction;

    public FavoritesPanel(FavoritesManager myFavoritesManager, UIFacade myUIFacade, IGanttProject myProject, TaskManager myTaskManager) {
        this.myFavoritesManager = myFavoritesManager;
        this.myUIFacade = myUIFacade;;
        this.myTaskManager = myTaskManager;
        myCopyFavAction = new CopyFavAction(myProject,myUIFacade, null);

    }

    public void setVisible(boolean isVisible){
        if (isVisible) {
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
                }
            };
            if (myFavoritesManager.getNFavorites() > 0) {
                myUIFacade.createDialog(contentPane, new Action[]{okAction, myCopyFavAction}, "Favorites").show();
            }else{
                myUIFacade.createDialog(contentPane, new Action[]{okAction}, "Favorites").show();
            }
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
        final JComponent mainPage = builder.buildPlanePage(new GPOptionGroup[]{});
        mainPage.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        builder.setUiFacade(myUIFacade);

        JPanel jp = new JPanel(new BorderLayout(10, 10));
        JLabel label = new JLabel("Favorites:");
        DefaultListModel<Task> list = new DefaultListModel<Task>();
        Iterator<Task> it = myFavoritesManager.favoritesIDIterator();
        while(it.hasNext()){
            list.addElement(it.next());
        }
        if(list.isEmpty()){
            list.addElement(null);
        }
        final JList<Task> favoritesList = new JList<Task>(list);
        favoritesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JFrame f = new JFrame();
                Task t = favoritesList.getSelectedValue();
                if(evt.getClickCount() == 1){
                    myCopyFavAction.setFavTask(t);
                }
            }
        });
        jp.add(favoritesList);
        mainPage.add(jp);
        return mainPage;

    }



}


