/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * This is a FileFilter that filters out hidden files.
 * @author Milo Steier
 */
public class HiddenFileFilter implements FileFilter{
    /**
     * This constructs an HiddenFileFilter that filters out hidden files.
     */
    public HiddenFileFilter(){
    }
    @Override
    public boolean accept(File pathname) {
        return pathname != null && !pathname.isHidden();
    }
}
