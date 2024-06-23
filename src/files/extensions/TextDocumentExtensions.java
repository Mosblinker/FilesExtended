/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.extensions;

import static files.FilesExtended.generateExtensionFilter;
import javax.swing.filechooser.FileFilter;

/**
 * This contains the file extensions for text documents.
 * @author Milo Steier
 */
public class TextDocumentExtensions {
    /**
     * This class cannot be constructed.
     */
    private TextDocumentExtensions(){}
    /**
     * This has the extension for a text file.
     */
    public static final String TXT = "txt";
    /**
     * This is a FileFilter for Text Documents.
     */
    public static final FileFilter TEXT_FILTER = generateExtensionFilter(
            "Text Documents",TXT);
}
