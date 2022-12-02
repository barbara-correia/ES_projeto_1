/*
GanttProject is an opensource project management tool.
Copyright (C) 2003-2011 GanttProject Team

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
package net.sourceforge.ganttproject.gui.tags;

import biz.ganttproject.core.option.*;
import net.sourceforge.ganttproject.action.CancelAction;
import net.sourceforge.ganttproject.action.OkAction;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.options.OptionsPageBuilder;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.Tag;
import net.sourceforge.ganttproject.task.TagImpl;
import net.sourceforge.ganttproject.task.TagManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * This class constructs the window to create a new tag.
 */
public class TagNewDialog {

    private static final String PANEL_TITLE = "Adicionar etiqueta";
    private static final String ENTER_NAME = "Insira nome da etiqueta";

    private static final String TAG_COLOR = "Cor da etiqueta";
    private boolean change;

    private final StringOption myNameField = new DefaultStringOption("name");

    private final GPOptionGroup myGroup;

    private ColorOption tagColorOption = new DefaultColorOption(TAG_COLOR);

    private final UIFacade myUIFacade;

    private final TagManager myTagManager;

   /* private final GPAction mySetDefaultColorAction = new GPAction("defaultColor") {
        @Override
        public void actionPerformed(ActionEvent e) {
            tagColorOption.setValue(Color.red);
        }
    };*/

    public TagNewDialog(UIFacade uiFacade, TagManager tagManager) {
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

    /**
     * This method loads the name field and the field to choose the color that will be associated with the tag.
     */
    private void loadFields() {
        myNameField.setValue(ENTER_NAME);
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
        Tag nTag = new TagImpl(myNameField.getValue(), tagColorOption.getValue());
        myTagManager.addTag(nTag);
    }
}
