/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author moscac
 */
public class TemplateProperties {
    
    private List<TemplateProperty> list;
    
    public TemplateProperties() {
        list = new ArrayList<>();        
    }

    public List<TemplateProperty> getList() {
        return list;
    }

    public void setList(List<TemplateProperty> list) {
        this.list = list;
    }
    
}
