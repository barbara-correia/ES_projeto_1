/*
GanttProject is an opensource project management tool.
Copyright (C) 2005-2011 GanttProject Team

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
package net.sourceforge.ganttproject;

import com.google.common.base.Function;
import net.sourceforge.ganttproject.chart.Chart;
import net.sourceforge.ganttproject.chart.overview.GPToolbar;
import net.sourceforge.ganttproject.chart.overview.ToolbarBuilder;
import net.sourceforge.ganttproject.gui.UIConfiguration;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.view.GPFavoriteView;
import net.sourceforge.ganttproject.gui.view.GPView;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

class FavoriteGanttChartTabContentPanel extends ChartTabContentPanel implements GPView {
    private final Container myTaskTree;
    private final JComponent myGanttChart;
    private final TreeTableContainer myTreeFacade;
    private final UIFacade myWorkbenchFacade;
    private JComponent myComponent;

    FavoriteGanttChartTabContentPanel(IGanttProject project, UIFacade workbenchFacade, TreeTableContainer treeFacade,
                              JComponent ganttChart, UIConfiguration uiConfiguration) {
        super(project, workbenchFacade, workbenchFacade.getGanttChart());
        myWorkbenchFacade = workbenchFacade;
        myTreeFacade = treeFacade;
        myTaskTree = (Container) treeFacade.getTreeComponent();
        myGanttChart = ganttChart;
        // FIXME KeyStrokes of these 2 actions are not working...
        addTableResizeListeners(myTaskTree, myTreeFacade.getTreeTable().getScrollPane().getViewport());
    }


    JComponent getComponent() {
        if (myComponent == null) {
            myComponent = createContentComponent();
        }
        return myComponent;
    }

    @Override
    protected Component createButtonPanel() {
        ToolbarBuilder builder = new ToolbarBuilder()
                .withHeight(24)  //barra de comandos do painel lateral
                .withSquareButtons()
                .withDpiOption(myWorkbenchFacade.getDpiOption())
                .withLafOption(myWorkbenchFacade.getLafOption(), new Function<String, Float>() {
                    @Override
                    public Float apply(@Nullable String s) {
                        return (s.indexOf("nimbus") >= 0) ? 2f : 1f;
                    }
                });
        final GPToolbar toolbar = builder.build();
        return toolbar.getToolbar();
    }

    @Override
    protected Component getChartComponent() {
        return myGanttChart;
    }

    @Override
    protected Component getTreeComponent() {
        return myTaskTree;
    }

    // //////////////////////////////////////////////
    // GPView
    @Override
    public void setActive(boolean active) {
        if (active) {
            myTaskTree.requestFocus();
            myTreeFacade.getNewAction().updateAction();
        }
    }

    @Override
    public Chart getChart() {
        return myWorkbenchFacade.getGanttChart();
    }

    @Override
    public Component getViewComponent() { return getComponent();}
}
