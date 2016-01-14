/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moscaville.util;

/**
 *
 * @author moscac
 */
public class Utility {

    public static final String DATA_DIRECTORY = "CSVPARSER_DATA_DIRECTORY";

    public static String getEnvironmentVariable(String name, String defaultValue) {
        return System.getenv(DATA_DIRECTORY) != null ? System.getenv(DATA_DIRECTORY) : defaultValue;
    }

}
