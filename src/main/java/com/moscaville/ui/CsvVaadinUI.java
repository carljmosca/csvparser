/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.ui;

import com.moscaville.manager.TemplateManager;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
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
    private TextField tfTemplateFileName;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildMainLayout();
        setContent(mainLayout);
    }

    private void buildMainLayout() {
        setSizeFull();
        mainLayout = new VerticalLayout();
        buildTemplateGridHeader();
        buildTemplateGrid();
        mainLayout.setSpacing(true);
        mainLayout.addComponent(dataGrid);
        dataGrid.setWidth("100%");
        dataGrid.setHeight("300px");
    }

    private void buildTemplateGridHeader() {
        HorizontalLayout templateGridHeaderLayout = new HorizontalLayout();
        templateGridHeaderLayout.setSpacing(true);
        templateGridHeaderLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Button btnNew = new Button("New", FontAwesome.FILE);
        btnNew.setDescription("New template file");
        btnNew.addClickListener((Button.ClickEvent event) -> {
            templateManager.newTemplate();
        });
        templateGridHeaderLayout.addComponent(btnNew);

        Button btnOpen = new Button("Open", FontAwesome.FILE_O);
        btnOpen.setDescription("Open template file");
        btnOpen.addClickListener((Button.ClickEvent event) -> {
            fileChooser.setFileExtension(FileChooser.FILE_EXTENSION_TEMPLATE);
            addWindow(fileChooser);
        });
        templateGridHeaderLayout.addComponent(btnOpen);

        Button btnSave = new Button("Save", FontAwesome.SAVE);
        tfTemplateFileName = new TextField();
        tfTemplateFileName.setDescription("template file name");
        tfTemplateFileName.setInputPrompt("template file name");
        tfTemplateFileName.setImmediate(true);
        tfTemplateFileName.addValueChangeListener((Property.ValueChangeEvent event) -> {
            btnSave.setEnabled(tfTemplateFileName.getValue() != null && tfTemplateFileName.getValue().length() > 0);
        });
        templateGridHeaderLayout.addComponent(tfTemplateFileName);

        FieldGroup binder = new FieldGroup(templateManager.getTemplateBeanItem());
        binder.setBuffered(false);
        binder.bind(tfTemplateFileName, "templateFileName");

        btnSave.setDescription("Save template file");
        btnSave.setImmediate(true);
        btnSave.setEnabled(false);
        btnSave.addClickListener((Button.ClickEvent event) -> {
            templateManager.saveTemplate();
        });
        templateGridHeaderLayout.addComponent(btnSave);

        Button btnData = new Button("Data", FontAwesome.DATABASE);
        btnData.setDescription("Load data");
        btnData.addClickListener((Button.ClickEvent event) -> {
            fileChooser.setFileExtension(FileChooser.FILE_EXTENSION_CSV);
            addWindow(fileChooser);
        });
        templateGridHeaderLayout.addComponent(btnData);

        Button btnImport = new Button("Import", FontAwesome.DOWNLOAD);

        templateGridHeaderLayout.addComponent(btnImport);
        mainLayout.addComponent(templateGridHeaderLayout);
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
