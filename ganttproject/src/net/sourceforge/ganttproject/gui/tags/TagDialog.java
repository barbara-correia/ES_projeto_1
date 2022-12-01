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
import net.sourceforge.ganttproject.action.GPAction;
import net.sourceforge.ganttproject.action.OkAction;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.options.OptionsPageBuilder;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.Tag;
import net.sourceforge.ganttproject.task.TagImpl;
import org.jdesktop.swingx.JXHyperlink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TagDialog {
    private boolean change;

    private static final GanttLanguage language = GanttLanguage.getInstance();

    private JTabbedPane tabbedPane;

    private final StringOption myNameField = new DefaultStringOption("name");

    private final GPOptionGroup myGroup;

    private ColorOption tagColorOption = new DefaultColorOption("Cor da etiqueta");

    private final UIFacade myUIFacade;

   /* private final GPAction mySetDefaultColorAction = new GPAction("defaultColor") {
        @Override
        public void actionPerformed(ActionEvent e) {
            tagColorOption.setValue(Color.red);
        }
    };*/

    public TagDialog(UIFacade uiFacade) {
        myUIFacade = uiFacade;
        myGroup = new GPOptionGroup("", new GPOption[]{myNameField});
        myGroup.setTitled(false);
    }

    public boolean result() {
        return change;
    }

    public void setVisible(boolean isVisible) {
        if (isVisible) {
            loadFields();
            Component contentPane = getComponent();
            OkAction okAction = new OkAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
            myUIFacade.createDialog(contentPane, new Action[]{okAction, cancelAction}, "Tag").show();
        }
    }

    private void loadFields() {
        myNameField.setValue("Enter tag name");
        tagColorOption.setValue(Color.red);
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
        final JComponent mainPage = builder.buildPlanePage(new GPOptionGroup[]{myGroup});
        mainPage.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        builder.setUiFacade(myUIFacade);
        JPanel colorBox = new JPanel(new BorderLayout(5, 0));
        colorBox.add(new JLabel("Cor da etiqueta: ", JLabel.LEFT));
        colorBox.add(builder.createColorComponent(tagColorOption).getJComponent(), BorderLayout.EAST);
        mainPage.add(colorBox);
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Criar", new ImageIcon(getClass().getResource("/icons/properties_16.gif")),
                mainPage);
        tabbedPane.addFocusListener(new FocusAdapter() {
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
        return tabbedPane;
    }

    private void okButtonActionPerformed() {
        applyChanges();
        change = true;
    }

    private void applyChanges() {
        Tag nTag = new TagImpl(myNameField.getValue(), tagColorOption.getValue());
        //por tag manager
    }
}

