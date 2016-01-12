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
import com.vaadin.data.util.BeanItemContainer;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moscac
 */
public class TemplateManager {

    private final TemplateProperties templateProperties;
    BeanItemContainer<TemplateProperty> container;
    private String fileName;
    private final List<String> inputColumns;
    private String[] headers;

    public TemplateManager() {
        templateProperties = new TemplateProperties();
        inputColumns = new ArrayList<>();
        container = new BeanItemContainer<>(TemplateProperty.class, templateProperties.getList());
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
            TemplateProperties tp = gson.fromJson(reader, TemplateProperties.class);
            container.removeAllItems();
            container.addAll(tp.getList());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveTemplate() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            TemplateProperties tp = new TemplateProperties();
            tp.getList().addAll(container.getItemIds());
            gson.toJson(tp, writer);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readData() {

        CsvParserSettings parserSettings = new CsvParserSettings();

        parserSettings.setLineSeparatorDetectionEnabled(true);

        RowListProcessor rowProcessor = new RowListProcessor();

        parserSettings.setRowProcessor(rowProcessor);

        parserSettings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(parserSettings);

        // the 'parse' method will parse the file and delegate each parsed row to the RowProcessor you defined
        parser.parse(getReader("/Lumiary_commerce_data_demo_151130.csv"));

        // get the parsed records from the RowListProcessor here.
        // Note that different implementations of RowProcessor will provide different sets of functionalities.
        headers = rowProcessor.getHeaders();
        List<String[]> rows = rowProcessor.getRows();

    }

    public Reader getReader(String relativePath) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream("/Users/moscac/Downloads" + relativePath), "UTF-8");
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reader;
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

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

}
