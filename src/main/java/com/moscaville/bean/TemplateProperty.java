/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.bean;

/**
 *
 * @author moscac
 */
public class TemplateProperty {
    
    private Long id;
    private String inputColumn;
    private String dataColumn;
    private String dataType;
    private boolean required;
    
    public TemplateProperty(String inputColumn, String dataColumn, String dataType, boolean required) {
        this.id = System.currentTimeMillis();
        this.inputColumn = inputColumn;
        this.dataColumn = dataColumn;
        this.dataType = dataType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputColumn() {
        return inputColumn;
    }

    public void setInputColumn(String inputColumn) {
        this.inputColumn = inputColumn;
    }

    public String getDataColumn() {
        return dataColumn;
    }

    public void setDataColumn(String dataColumn) {
        this.dataColumn = dataColumn;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
    
}
