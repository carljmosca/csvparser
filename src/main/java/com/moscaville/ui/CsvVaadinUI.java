/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.ui;

import com.moscaville.manager.TemplateManager;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class CsvVaadinUI extends UI {

    private VerticalLayout mainLayout;
    private TemplateGrid templateGrid;
    private TemplateManager templateManager;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildMainLayout();
        setContent(mainLayout);
    }

    private void buildMainLayout() {
        mainLayout = new VerticalLayout();
        buildMenu();
        mainLayout.setSpacing(true);
        templateGrid = new TemplateGrid();
        templateManager = new TemplateManager();
        templateGrid.setTemplateManager(templateManager);
        mainLayout.addComponent(templateGrid);
    }

    private void buildMenu() {
        MenuBar mainMenu = new MenuBar();
        mainLayout.addComponent(mainMenu);

        MenuBar.Command menuCommand = (MenuItem selectedItem) -> {
            System.out.println(selectedItem.getDescription());
        };

        MenuItem miTemplates = mainMenu.addItem("Template", null, null);
        MenuItem miDataFile = mainMenu.addItem("Data", null, null);

        MenuItem miNewTemplate = miTemplates.addItem("New", null, menuCommand);
        MenuItem miOpenTemplate = miTemplates.addItem("Open", (MenuItem selectedItem) -> {
            templateManager.loadTemplate();
        });
        MenuItem miSaveTemplate = miTemplates.addItem("Save", (MenuItem selectedItem) -> {
            templateManager.saveTemplate();
        });
        MenuItem miAddTemplateProperty = miTemplates.addItem("Add Property", (MenuItem selectedItem) -> {
            templateManager.addProperty();
        });
        MenuItem miDeleteTemplateProperty = miTemplates.addItem("Delete Property", (MenuItem selectedItem) -> {
            Object itemId = templateGrid.getSelectedRow();
            if (itemId != null) {
                templateManager.getContainer().removeItem(itemId);
            }
        });
        //miDeleteTemplateProperty.setEnabled(false);
        MenuItem miOpenDataFile = miDataFile.addItem("Open", null, menuCommand);

    }

}
