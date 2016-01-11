/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.ui;

import com.moscaville.manager.TemplateManager;
import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ButtonRenderer;
import java.util.Arrays;

/**
 *
 * @author moscac
 */
public class TemplateGrid extends Grid {

    //private static final String FORMAT = "%1$td/%1$tm/%1$tY %1$tH:%1$tM:%1$tS";
    private TemplateManager templateManager;
    private GeneratedPropertyContainer wrapperContainer;
    private ComboBox cmbDataType;
    private ComboBox cmbInputColumn;
    private final String[] DATA_TYPES = {"Date", "Integer", "String"};

    public TemplateGrid() {

    }

    private void init() {
        setEditorEnabled(true);
        wrapperContainer = new GeneratedPropertyContainer(templateManager.getContainer());
        wrapperContainer.removeContainerProperty("id");
        setContainerDataSource(wrapperContainer);
        setColumnOrder("dataColumn", "inputColumn", "dataType");
        
        setEditors();
        wrapperContainer.addGeneratedProperty("delete", new PropertyValueGenerator<String>() {
            @Override
            public String getValue(Item item, Object itemId, Object propertyId) {
                return "Delete";
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        });
        getColumn("delete").setRenderer(new ButtonRenderer(event -> {
            Object itemId = event.getItemId();
            templateManager.getContainer().removeItem(itemId);
        }));
        getColumns().stream().forEach(c -> c.setSortable(false));
        setHeaderVisible(true);
    }

    private void setEditors() {
        cmbDataType = new ComboBox();
        cmbDataType.addItems(Arrays.asList(DATA_TYPES));
        getColumn("dataType").setEditorField(cmbDataType);
        cmbInputColumn = new ComboBox();
        getColumn("inputColumn").setEditorField(cmbInputColumn);
    }

    public TemplateManager getTemplateManager() {
        return templateManager;
    }

    public void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
        init();
    }
    
    
}
