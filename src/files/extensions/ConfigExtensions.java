/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.extensions;

import static files.FilesExtended.generateExtensionFilter;
import javax.swing.filechooser.FileFilter;

/**
 * This contains the file extensions for config files.
 * @author Milo Steier
 */
public class ConfigExtensions {
    /**
     * This class cannot be constructed.
     */
    private ConfigExtensions(){}
    /**
     * This has the extension for a config file.
     */
    public static final String CFG = "cfg";
    /**
     * This is a FileFilter for config files.
     */
    public static final FileFilter CONFIG_FILTER = generateExtensionFilter(
            "Config Files",CFG);
    /**
     * This finds the setting that the given argument String represents and 
     * returns the index in the given array.
     * @param settings The array of settings.
     * @param arg The argument to find.
     * @return The index of the argument in the array, or -1 if not found.
     */
    public static int getSettingNumber(String[] settings, String arg){
            // A for loop to find the given argument
        for (int pos = 0; pos < settings.length; pos++)
                // If the setting at the index is not null and equals the 
            if (settings[pos] != null &&settings[pos].equals(arg))//argument
                return pos;
        return -1;
    }
    /**
     * This finds the setting header that the given header String represents 
     * and returns the index for that header's row in the given two dimensional 
     * array. <p>
     * Precondition: The setting header String is assumed to be the String in 
     * the first column of each row. 
     * @param settings The two-dimensional array of settings.
     * @param arg The header to find.
     * @return The index of the corresponding header's row in the array, or -1 
     * if not found.
     */
    public static int getSettingHeader(String[][] settings, String arg){
            // A for loop to go through the rows
        for (int pos = 0; pos < settings.length; pos++)
                // If the row is not empty and the first element is not null and
                // matches the given string
            if (settings[pos].length > 0 && settings[pos][0] != null && 
                    settings[pos][0].equals(arg))
                return pos;
        return -1;
    }
}
