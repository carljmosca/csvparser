/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class CsvVaadinUI extends UI {

    private VerticalLayout mainLayout;
    private TemplateGrid templateGrid;
    private Button btnLoadData;
    private Button btnOpenConfigurationFile;
    private Button btnNewConfigurationFile;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildMainLayout();
        setContent(mainLayout);
    }

    private void buildMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.addComponent(buildButtonLayout());
        templateGrid = new TemplateGrid();
        mainLayout.addComponent(templateGrid);
    }
    
    private HorizontalLayout buildButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        
        btnNewConfigurationFile = new Button("New", (Button.ClickEvent event) -> {

        });
        btnNewConfigurationFile.setDescription("Create new template");        
        buttonLayout.addComponent(btnNewConfigurationFile);

        btnOpenConfigurationFile = new Button("Open", (Button.ClickEvent event) -> {
            
        });
        btnOpenConfigurationFile.setDescription("Open template file");
        buttonLayout.addComponent(btnOpenConfigurationFile);
        
        btnLoadData = new Button("Load", (Button.ClickEvent event) -> {
            
        });
        btnLoadData.setDescription("Load data");
        buttonLayout.addComponent(btnLoadData);
        
        return buttonLayout;
    }
}
