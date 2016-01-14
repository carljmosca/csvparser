/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.ui;

import com.moscaville.manager.TemplateManager;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author moscac
 */
@Component
public class TemplateGrid extends Grid {

    //private static final String FORMAT = "%1$td/%1$tm/%1$tY %1$tH:%1$tM:%1$tS";
    @Autowired
    private TemplateManager templateManager;
    private GeneratedPropertyContainer wrapperContainer;
    private ComboBox cmbDataType;
    private ComboBox cmbInputColumn;
    private final String[] DATA_TYPES = {"Date", "Integer", "String"};

    public TemplateGrid() {

    }

    @PostConstruct
    private void init() {
        setEditorEnabled(true);
        wrapperContainer = new GeneratedPropertyContainer(templateManager.getContainer());
        wrapperContainer.removeContainerProperty("id");
        setContainerDataSource(wrapperContainer);
        setColumnOrder("dataColumn", "inputColumn", "dataType", "required");        
        setEditors();
        //getColumns().stream().forEach(c -> c.setSortable(false));
        setHeaderVisible(true);
    }

    private void setEditors() {
        cmbDataType = new ComboBox();
        cmbDataType.addItems(Arrays.asList(DATA_TYPES));
        getColumn("dataType").setEditorField(cmbDataType);
        cmbInputColumn = new ComboBox();
        getColumn("inputColumn").setEditorField(cmbInputColumn);
    }    
    
}
