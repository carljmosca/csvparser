/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moscaville.bean.Template;
import com.moscaville.bean.TemplateProperty;
import com.moscaville.util.Utility;
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import java.beans.PropertyChangeEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 *
 * @author moscac
 */
@Service
public class TemplateManager {

    private Template template;
    private BeanItem<Template> templateBeanItem;
    private BeanItemContainer<TemplateProperty> container;
    private IndexedContainer dataContainer;
    private List<String> inputColumns;
    private String[] dataHeaders;
    private final List<Observer> observers;
    private String dataDirectory;

    public TemplateManager() {
        observers = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        dataDirectory = Utility.getEnvironmentVariable(Utility.DATA_DIRECTORY, ".");
        template = new Template();
        inputColumns = new ArrayList<>();
        container = new BeanItemContainer<>(TemplateProperty.class, template.getList());
        dataContainer = new IndexedContainer();
        dataHeaders = new String[]{};
        templateBeanItem = new BeanItem<>(template);
        template.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            System.out.println(evt.getPropertyName());
            if (Template.PROP_TEMPLATEFILENAME.equals(evt.getPropertyName())) {
                loadTemplate();
            } else if (Template.PROP_DATAFILENAME.equals(evt.getPropertyName())) {
                readCsvData();
            }
        });
    }

    public void addProperty() {
        container.addBean(new TemplateProperty("", "data column", "String", false));
    }

    public void newTemplate() {

    }

    public void loadTemplate() {
        try (Reader reader = new InputStreamReader(new FileInputStream(
                getFileNameProper(templateBeanItem.getBean().getTemplateFileName(), "json")), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            Template tp = gson.fromJson(reader, Template.class
            );
            container.removeAllItems();
            container.addAll(tp.getList());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TemplateManager.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(TemplateManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveTemplate() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(
                getFileNameProper(templateBeanItem.getBean().getTemplateFileName(), "json")), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            Template tp = new Template();
            tp.getList().addAll(container.getItemIds());
            gson.toJson(tp, writer);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TemplateManager.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(TemplateManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readCsvData() {

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(parserSettings);
        // the 'parse' method will parse the file and delegate each parsed row to the RowProcessor you defined
        parser.parse(getReader("csv"));
        // get the parsed records from the RowListProcessor here.
        // Note that different implementations of RowProcessor will provide different sets of functionalities.
        dataContainer.removeAllItems();
        for (String s : dataHeaders) {
            dataContainer.removeContainerProperty(s);
        }
        dataHeaders = rowProcessor.getHeaders();
        dataContainer.addContainerProperty("id", Integer.class, null);
        for (String s : dataHeaders) {
            dataContainer.addContainerProperty(s, String.class, "");
        }
        List<String[]> data = rowProcessor.getRows();
        int rows = 0;
        for (String[] d : data) {
            Item item = dataContainer.addItem(rows);
            for (int i = 0; i < dataHeaders.length; i++) {
                try {
                    item.getItemProperty(dataHeaders[i]).setValue(d[i]);
                } catch (NullPointerException ex) {
                    Logger.getLogger(TemplateManager.class
                            .getName()).log(Level.WARNING, null, ex);
                }
            }
            if (++rows > 100) {
                break;
            }
        }
    }

    private Reader getReader(String extension) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(
                    getFileNameProper(templateBeanItem.getBean().getDataFileName(), extension)), "UTF-8");
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(TemplateManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return reader;
    }

    public BeanItemContainer<TemplateProperty> getContainer() {
        return container;
    }

    public List<String> getInputColumns() {
        return inputColumns;
    }

    public String[] getDataHeaders() {
        return dataHeaders;
    }

    public IndexedContainer getDataContainer() {
        return dataContainer;
    }

    public BeanItem<Template> getTemplateBeanItem() {
        return templateBeanItem;
    }

    public void setTemplateBeanItem(BeanItem<Template> templateBeanItem) {
        this.templateBeanItem = templateBeanItem;
    }

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    private String getFileNameProper(String fileName, String extension) {
        StringBuilder result = new StringBuilder();
        result.append(dataDirectory);
        result.append("/");
        result.append(fileName);
        if (!fileName.toLowerCase().endsWith(extension)) {
            result.append(".").append(extension);
        }
        return result.toString();
    }

}
