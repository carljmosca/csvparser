/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.ui;

import com.moscaville.manager.TemplateManager;
import com.moscaville.util.Utility;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.event.MouseEvents;
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
    private String fileExtension;
    @Autowired
    private TemplateManager templateManager;
    public final static String FILE_EXTENSION_TEMPLATE = "json";
    public final static String FILE_EXTENSION_CSV = "csv";

    public FileChooser() {
    }

    @PostConstruct
    private void init() {
        fileExtension = "*";
        mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        buildFileChooser();
        buildButtonLayout();
        setContent(mainLayout);
    }

    private void buildFileChooser() {
        fileChooser = new TreeTable("Data Files");
        fileChooser.setImmediate(true);
        fileChooser.setSelectable(true);
        fileChooser.addListener((Event event) -> {
            btnSelect.setEnabled(fileChooser.getValue() != null);
        });
        mainLayout.addComponent(fileChooser);
    }

    private void readDirectory() {
        String caption = "";
        if (FILE_EXTENSION_CSV.equals(fileExtension)) {
            caption = "Data files";
        } else if (FILE_EXTENSION_TEMPLATE.equals(fileExtension)) {
            caption = "Template files";
        }
        fileChooser.setCaption(caption);
        File folder = new File(Utility.getEnvironmentVariable(Utility.DATA_DIRECTORY + "." + fileExtension, "."));
        FilesystemContainer container = new FilesystemContainer(folder);
        fileChooser.setContainerDataSource(container);
        fileChooser.setItemIconPropertyId("Icon");
        fileChooser.setVisibleColumns(new Object[]{"Name", "Size", "Last Modified"});
    }

    private void buildButtonLayout() {
        buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        btnSelect = new Button("Select");
        btnSelect.setEnabled(false);
        btnSelect.addClickListener((Button.ClickEvent event) -> {
            selectFile();
        });
        buttonLayout.addComponent(btnSelect);
        btnCancel = new Button("Cancel");
        btnCancel.addClickListener((Button.ClickEvent event) -> {
            close();
        });
        buttonLayout.addComponent(btnCancel);
        mainLayout.addComponent(buttonLayout);
        addClickListener((MouseEvents.ClickEvent event) -> {
            if (event.isDoubleClick()) {
                selectFile();
            }
        });
    }

    private void selectFile() {
        if (fileChooser.getValue() == null) {
            return;
        }
        File selectedFile = (File) fileChooser.getValue();
        if (FILE_EXTENSION_CSV.equals(fileExtension)) {
            templateManager.setDataFileName(selectedFile.getAbsoluteFile().getAbsolutePath());
        } else if (FILE_EXTENSION_TEMPLATE.equals(fileExtension)) {
            templateManager.setTemplateFileName(selectedFile.getAbsoluteFile().getAbsolutePath());
        }
        close();
    }

    public boolean isSelected() {
        return selected;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        readDirectory();
    }

}
