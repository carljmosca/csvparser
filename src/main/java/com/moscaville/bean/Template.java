/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.bean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author moscac
 */
public class Template {

    private List<TemplateProperty> list;
    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private String templateFileName;
    private String dataFileName;
    public static final String PROP_TEMPLATEFILENAME = "templateFileName";
    public static final String PROP_DATAFILENAME = "dataFileName";

    public Template() {
        list = new ArrayList<>();
        templateFileName = "";
        dataFileName = "";
    }

    public List<TemplateProperty> getList() {
        return list;
    }

    public void setList(List<TemplateProperty> list) {
        this.list = list;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        String oldTemplateFileName = this.templateFileName;
        this.templateFileName = templateFileName;
        propertyChangeSupport.firePropertyChange(PROP_TEMPLATEFILENAME, oldTemplateFileName, templateFileName);
    }

    public String getDataFileName() {
        return dataFileName;
    }

    public void setDataFileName(String dataFileName) {
        String oldDataFileName = this.dataFileName;
        this.dataFileName = dataFileName;
        propertyChangeSupport.firePropertyChange(PROP_DATAFILENAME, oldDataFileName, dataFileName);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
