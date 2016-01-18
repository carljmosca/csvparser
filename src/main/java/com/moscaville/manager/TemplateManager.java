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
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
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
import java.util.Observable;
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
public class TemplateManager extends Observable {

    private TemplateProperties templateProperties;
    private BeanItemContainer<TemplateProperty> container;
    private IndexedContainer dataContainer;
    private String templateFileName;
    private String dataFileName;
    private List<String> inputColumns;
    private String[] dataHeaders;
    private final List<Observer> observers;

    public TemplateManager() {
        observers = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        templateProperties = new TemplateProperties();
        inputColumns = new ArrayList<>();
        container = new BeanItemContainer<>(TemplateProperty.class, templateProperties.getList());
        dataContainer = new IndexedContainer();
        dataHeaders = new String[]{};
    }

    public void addProperty() {
        container.addBean(new TemplateProperty("", "data column", "String", false));
    }

    public void newTemplate() {

    }

    public void loadTemplate() {
        try (Reader reader = new InputStreamReader(new FileInputStream(templateFileName), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            TemplateProperties tp = gson.fromJson(reader, TemplateProperties.class
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
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(templateFileName), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            TemplateProperties tp = new TemplateProperties();
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

    private void readCsvData() {

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(parserSettings);
        // the 'parse' method will parse the file and delegate each parsed row to the RowProcessor you defined
        parser.parse(getReader());
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

    private Reader getReader() {
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(dataFileName), "UTF-8");
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(TemplateManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return reader;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String getDataFileName() {
        return dataFileName;
    }

    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
        boolean processed = false;
        if (dataFileName.toLowerCase().endsWith(".csv")) {
            readCsvData();
            processed = true;
        }
        if (processed) {
            notifyObservers();
        }
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

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        observers.stream().forEach((o) -> {
            o.update(this, "");
        });
    }

}
