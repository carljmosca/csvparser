/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.manager;

import com.moscaville.bean.TemplateProperty;
import com.vaadin.data.util.BeanItemContainer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author moscac
 */
public class TemplateManager {

    private final List<TemplateProperty> templateProperties;
    BeanItemContainer<TemplateProperty> container;
    private String fileName;

    public TemplateManager() {
        templateProperties = new ArrayList<>();
        container = new BeanItemContainer<>(TemplateProperty.class, templateProperties);
        init();
    }

    private void init() {
        container.addBean(new TemplateProperty("CITY", "city", "String"));
//                Collection<TemplateProperty> messages = Arrays.asList(
//                new TemplateProperty("FIRST_NAME", "fn", "String"),
//                new TemplateProperty("LAST_NAME", "ln", "String"),
//                new TemplateProperty("ADDRESS", "street", "String"),
//                new TemplateProperty("CITY", "city", "String"));
//        Container.Indexed indexed = new BeanItemContainer<>(TemplateProperty.class, messages);
    }

    public void loadTemplate() {

    }

    public void saveTemplate() {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BeanItemContainer<TemplateProperty> getContainer() {
        return container;
    }

}
