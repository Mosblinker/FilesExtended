/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * This is a FileFilter that, by default, only accepts directories. This can be
 * constructed with another FileFilter to allow for non-directory files to be 
 * included. This can be useful for listing the files within a file tree when 
 * using a FileFilter that would otherwise have prevented subdirectories from 
 * being checked.
 * @author Milo Steier
 */
public class DirectoryFilter implements FileFilter{
    /**
     * This is an optional additional FileFilter that can be used to include 
     * non-directories.
     */
    private final FileFilter filter;
    /**
     * This constructs a DirectoryFilter with the given non-directory 
     * FileFilter.
     * @param filter A FileFilter that is used to determine if a non-directory 
     * file is to be included, or null if only directories are to be included.
     */
    public DirectoryFilter(FileFilter filter){
        this.filter = filter;
    }
    /**
     * This constructs a DirectoryFilter that will only include directories.
     */
    public DirectoryFilter(){
        this(null);
    }
    /**
     * This returns the FileFilter used to determine whether a non-directory 
     * file is to be included.
     * @return The FileFilter used to include non-directory files, or null if 
     * this DirectoryFilter only includes directories.
     * @see #accept(java.io.File) 
     */
    public FileFilter getNonDirectoryFileFilter(){
        return filter;
    }
    /**
     * {@inheritDoc } If the pathname denotes a directory, then this will 
     * return true. If the pathname does not denote a directory, then this will
     * return true if there is an additional FileFilter and if calling {@code 
     * getNonDirectoryFileFilter().accept(pathname)} returns true, otherwise 
     * this returns false.
     * @param pathname {@inheritDoc }
     * @return {@inheritDoc }
     * @see #getNonDirectoryFileFilter() 
     */
    @Override
    public boolean accept(File pathname) {
        return pathname != null && (pathname.isDirectory() || (filter != null 
                && filter.accept(pathname)));
    }
}
