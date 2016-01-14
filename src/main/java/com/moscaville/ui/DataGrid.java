/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.ui;

import com.moscaville.manager.TemplateManager;
import com.vaadin.data.Container;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.ui.Grid;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author moscac
 */
@Component
public class DataGrid extends Grid {

    @Autowired
    private TemplateManager templateManager;
    private GeneratedPropertyContainer wrapperContainer;

    @PostConstruct
    private void init() {
        setEditorEnabled(true);
        wrapperContainer = new GeneratedPropertyContainer(templateManager.getDataContainer());
        setContainerDataSource(wrapperContainer);
        wrapperContainer.removeContainerProperty("id");
        getColumns().stream().forEach(c -> c.setSortable(false));
        setHeaderVisible(true);
        templateManager.getDataContainer().addItemSetChangeListener((Container.ItemSetChangeEvent event) -> {
            //setColumns((Object)templateManager.getDataHeaders());
        });
    }
}
