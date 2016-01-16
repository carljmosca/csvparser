/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.ui;

import com.moscaville.manager.TemplateManager;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class CsvVaadinUI extends UI {

    private VerticalLayout mainLayout;
    private HorizontalLayout buttonLayout;
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
        buildButtons();
        buildTemplateGrid();
        mainLayout.setSpacing(true);
        mainLayout.addComponent(dataGrid);
        dataGrid.setWidth("100%");
        dataGrid.setHeight("300px");
    }

    private void buildButtons() {
        buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);

        buttonLayout.addComponent(new Button("New", FontAwesome.FILE));

        Button btnOpen = new Button("Open", FontAwesome.FILE_O);
        btnOpen.setDescription("Open template file");
        btnOpen.addClickListener((Button.ClickEvent event) -> {
            templateManager.loadTemplate();
        });
        buttonLayout.addComponent(btnOpen);

        Button btnSave = new Button("Save", FontAwesome.SAVE);
        btnSave.setDescription("Save template file");
        btnSave.addClickListener((Button.ClickEvent event) -> {
            templateManager.saveTemplate();
        });
        buttonLayout.addComponent(btnSave); 
        
        Button btnData = new Button("Data", FontAwesome.DATABASE);
        btnData.setDescription("Load data");
        btnData.addClickListener((Button.ClickEvent event) -> {
            addWindow(fileChooser);
        });
        buttonLayout.addComponent(btnData);
        mainLayout.addComponent(buttonLayout);
    }

    private void buildTemplateGrid() {
        VerticalLayout templateGridLayout = new VerticalLayout();
        templateGridLayout.setSpacing(true);
        templateGrid.setWidth("100%");
        templateGrid.setHeight("300px");
        templateGridLayout.addComponent(templateGrid);
        HorizontalLayout tgButtonLayout = new HorizontalLayout();
        tgButtonLayout.setSpacing(true);
        Button btnAddTemplate = new Button("Add", FontAwesome.PLUS_SQUARE);
        btnAddTemplate.setDescription("Add template");
        btnAddTemplate.addClickListener((Button.ClickEvent event) -> {
            templateManager.addProperty();
        });
        tgButtonLayout.addComponent(btnAddTemplate);
        Button btnDeleteTemplate = new Button("Delete", FontAwesome.MINUS_SQUARE);
        btnDeleteTemplate.setDescription("Delete template");
        btnDeleteTemplate.addClickListener((Button.ClickEvent event) -> {
            Object itemId = templateGrid.getSelectedRow();
            if (itemId != null) {
                templateManager.getContainer().removeItem(itemId);
            }
        });
        tgButtonLayout.addComponent(btnDeleteTemplate);
        templateGridLayout.addComponent(tgButtonLayout);
        mainLayout.addComponent(templateGridLayout);
    }

}
