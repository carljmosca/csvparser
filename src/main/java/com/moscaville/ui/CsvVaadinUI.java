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
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class CsvVaadinUI extends UI {

    private VerticalLayout mainLayout;
    @Autowired
    private TemplateGrid templateGrid;
    @Autowired
    private DataGrid dataGrid;
    @Autowired
    private TemplateManager templateManager;
    @Autowired
    private FileChooser fileChooser;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildMainLayout();
        setContent(mainLayout);
    }

    private void buildMainLayout() {
        setSizeFull();
        mainLayout = new VerticalLayout();
        buildMenu();
        mainLayout.setSpacing(true);
        mainLayout.addComponent(templateGrid);
        templateGrid.setWidth("100%");
        templateGrid.setHeight("300px");
        mainLayout.addComponent(dataGrid);
        dataGrid.setWidth("100%");
        dataGrid.setHeight("300px");
        
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
        MenuItem miOpenDataFile = miDataFile.addItem("Open", (MenuItem selectedItem) -> {
            addWindow(fileChooser);
        });

    }


}
