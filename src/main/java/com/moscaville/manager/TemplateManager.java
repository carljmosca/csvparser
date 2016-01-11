/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moscaville.bean.TemplateProperties;
import com.moscaville.bean.TemplateProperty;
import com.vaadin.data.util.BeanItemContainer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moscac
 */
public class TemplateManager {

    private final List<TemplateProperty> templateProperties;
    BeanItemContainer<TemplateProperty> container;
    private String fileName;
    private final List<String> inputColumns;

    public TemplateManager() {
        templateProperties = new ArrayList<>();
        inputColumns = new ArrayList<>();
        container = new BeanItemContainer<>(TemplateProperty.class, templateProperties);
        init();
    }

    private void init() {
        fileName = "templateproperties.json";
    }
    
    public void addProperty() {
        container.addBean(new TemplateProperty("", "data column", "String"));
    }

    public void loadTemplate() {
        try (Reader reader = new InputStreamReader(new FileInputStream(fileName), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            TemplateProperties templateProperties = gson.fromJson(reader, TemplateProperties.class);
            container.removeAllItems();
            container.addAll(templateProperties.getList());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveTemplate() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            TemplateProperties templateProperties = new TemplateProperties();
            templateProperties.getList().addAll(container.getItemIds());
            gson.toJson(templateProperties, writer);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public List<String> getInputColumns() {
        return inputColumns;
    }

}
