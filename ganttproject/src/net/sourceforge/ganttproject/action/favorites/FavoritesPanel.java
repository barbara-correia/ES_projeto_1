package net.sourceforge.ganttproject.action.favorites;

import biz.ganttproject.core.option.*;
import net.sourceforge.ganttproject.action.CancelAction;
import net.sourceforge.ganttproject.action.OkAction;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.options.OptionsPageBuilder;
import net.sourceforge.ganttproject.task.FavoritesManager;
import net.sourceforge.ganttproject.task.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Iterator;

/**
 * Generates the panel(popup window) with the list of favorite tasks.
 */
public class FavoritesPanel {

    private final FavoritesManager myFavoritesManager;
    private final UIFacade myUIFacade;
    private final StringOption myNameField;

    public FavoritesPanel(FavoritesManager myFavoritesManager, UIFacade myUIFacade) {
        this.myFavoritesManager = myFavoritesManager;
        this.myUIFacade = myUIFacade;
        myNameField = new DefaultStringOption("name");
    }

    public void setVisible(boolean isVisible){
        if (isVisible) {
            Component contentPane = getComponent();
            OkAction okAction = new OkAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   {
                       //myGroup.commit();
                   }
                }
            };
            myUIFacade.createDialog(contentPane, new Action[]{okAction}, "Favorites").show();
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
        JList<Task> favoritesList = new JList<Task>(list);
        jp.add(favoritesList);
        mainPage.add(jp);
        return mainPage;

    }
}
