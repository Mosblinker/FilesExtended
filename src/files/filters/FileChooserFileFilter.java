/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.filters;

import java.io.File;
import java.util.Objects;
import javax.swing.JFileChooser;

/**
 * This is a FileFilter that filters out files that cannot be selected in a 
 * given JFileChooser.
 * @author Milo Steier
 */
public class FileChooserFileFilter implements java.io.FileFilter{
    /**
     * The JFileChooser to use to determine if a file will be accepted.
     */
    private JFileChooser fc;
    /**
     * This constructs a FileChooserFileFilter with the given JFileChooser.
     * @param fc The JFileChooser to use (cannot be null).
     */
    public FileChooserFileFilter(JFileChooser fc){
        this.fc = Objects.requireNonNull(fc);
    }
    /**
     * This returns the JFileChooser that this uses to filter out files that 
     * cannot be selected.
     * @return The JFileChooser that this uses.
     */
    public JFileChooser getFileChooser(){
        return fc;
    }
    /**
     * This sets the JFileChooser to use to filter out files that cannot be 
     * selected.
     * @param fc The JFileChooser to use (cannot be null).
     */
    public void setFileChooser(JFileChooser fc){
        this.fc = Objects.requireNonNull(fc);
    }
    /**
     * This checks to see if the given abstract pathname represents a file that 
     * can be selected in the JFileChooser.
     * @param pathname {@inheritDoc }
     * @return {@inheritDoc }
     * @see JFileChooser#accept(java.io.File) 
     * @see JFileChooser#isFileHidingEnabled() 
     * @see JFileChooser#getFileSelectionMode() 
     */
    @Override
    public boolean accept(File pathname) {
            // If the pathname is null or the file chooser does not display 
        if (pathname == null || !fc.accept(pathname))   // the pathname
            return false;
            // If file hiding is enabled and the pathname is hidden
        else if (fc.isFileHidingEnabled() && pathname.isHidden())
            return false;
            // If directories cannot be selected and the pathname is a directory
        else if (!fc.isDirectorySelectionEnabled() && pathname.isDirectory())
            return false;
            // If files cannot be selected and the pathname is a normal file
        else if (!fc.isFileSelectionEnabled() && pathname.isFile())
            return false;
        return true;
    }
}
