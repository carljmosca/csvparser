/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.ui;

import com.moscaville.manager.TemplateManager;
import com.moscaville.util.Utility;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.io.File;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author moscac
 */
@Component
public class FileChooser extends Window {

    private TreeTable fileChooser;
    private VerticalLayout mainLayout;
    private HorizontalLayout buttonLayout;
    private Button btnSelect;
    private Button btnCancel;
    private boolean selected;
    @Autowired
    private TemplateManager templateManager;
    
    public FileChooser() {
    }

    @PostConstruct
    private void init() {
        mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        buildFileChooser();
        buildButtonLayout();
        setContent(mainLayout);
    }

    private void buildFileChooser() {
        File folder = new File(Utility.getEnvironmentVariable(Utility.DATA_DIRECTORY, "."));
        FilesystemContainer container = new FilesystemContainer(folder);
        fileChooser = new TreeTable("Data Files");
        fileChooser.setContainerDataSource(container);
        fileChooser.setItemIconPropertyId("Icon");
        fileChooser.setVisibleColumns(new Object[]{"Name", "Size", "Last Modified"});
        fileChooser.setImmediate(true);
        fileChooser.setSelectable(true);
        fileChooser.addListener((Event event) -> {
            btnSelect.setEnabled(fileChooser.getValue() != null);
        });
        mainLayout.addComponent(fileChooser);
    }
    
    private void buildButtonLayout() {
        buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        btnSelect = new Button("Select");
        btnSelect.setEnabled(false);
        btnSelect.addClickListener((Button.ClickEvent event) -> {
            File selectedFile = (File)fileChooser.getValue();
            templateManager.setFileName(selectedFile.getAbsoluteFile().getAbsolutePath());
            close();
        });
        buttonLayout.addComponent(btnSelect);
        btnCancel = new Button("Cancel");
        btnCancel.addClickListener((Button.ClickEvent event) -> {
            close();
        });
        buttonLayout.addComponent(btnCancel);
        mainLayout.addComponent(buttonLayout);
    }

    public boolean isSelected() {
        return selected;
    }
    
}
