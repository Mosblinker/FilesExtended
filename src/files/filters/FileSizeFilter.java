/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * This is a FileFilter to filter out files with sizes that are either too 
 * large or too small.
 * @author Milo Steier
 */
public class FileSizeFilter implements FileFilter{
    /**
     * The minimum size a file can be to be accepted. Negative values indicate 
     * that there is no limit to how small a file can be.
     */
    private long min;
    /**
     * The maximum size a file can be to be accepted. Negative values indicate 
     * that there is no limit to how large a file can be.
     */
    private long max;
    /**
     * This constructs a FileSizeFilter with the given minimum and maximum file 
     * sizes, in bytes. <p>
     * A negative minimum value will result in no limit to how small a file can 
     * be. <p>
     * A negative maximum value will result in no limit to how large a file can 
     * be. <p>
     * @param minimum The minimum size a file can be, in bytes.
     * @param maximum The maximum size a file can be, in bytes.
     */
    public FileSizeFilter(long minimum, long maximum){
        min = minimum;
        max = maximum;
    }
    /**
     * This constructs a FileSizeFilter with the given maximum file size, in 
     * bytes, and no limit for how small files can be. <p>
     * A negative maximum value results in no limit to how large a file can 
     * be. <p>
     * @param maximum The maximum size a file can be, in bytes.
     */
    public FileSizeFilter(long maximum){
        this(-1,maximum);
    }
    /**
     * This returns how small a file can be and still be included. <p>
     * A negative value indicates that there is no limit on how small a file 
     * can be.
     * @return The minimum size a file can be, in bytes.
     * @see #isMinimumSizeSet() 
     */
    public long getMinumum(){
        return min;
    }
    /**
     * This returns how large a file can be and still be included. <p>
     * A negative value indicates that there is no limit on how large a file 
     * can be.
     * @return The maximum size a file can be, in bytes.
     * @see #isMaximumSizeSet() 
     */
    public long getMaximum(){
        return max;
    }
    /**
     * This sets the minimum size a file can be while still being included. <p>
     * A negative value will result in no limit to how small a file can be.
     * @param minimum The minimum size a file can be, in bytes.
     */
    public void setMinimum(long minimum){
        min = minimum;
    }
    /**
     * This sets the maximum size a file can be while still being included. <p>
     * A negative value will result in no limit to how large a file can be.
     * @param maximum The maximum size a file can be, in bytes.
     */
    public void setMaximum(long maximum){
        max = maximum;
    }
    /**
     * This returns whether files will be filtered based on how small they 
     * are.
     * @return Whether files will be filtered based on how small they are.
     * @see #getMinumum() 
     */
    public boolean isMinimumSizeSet(){
        return min >= 0;
    }
    /**
     * This returns whether files will be filtered based on how large they 
     * are.
     * @return Whether files will be filtered based on how large they are.
     * @see #getMaximum() 
     */
    public boolean isMaximumSizeSet(){
        return max >= 0;
    }
    /**
     * This returns whether the given pathname is a normal file that has a size 
     * that is within the minimum and maximum file sizes, inclusive. If the 
     * pathname denotes a directory, then this will return false.
     * @param pathname {@inheritDoc }
     * @return {@inheritDoc }
     * @see #getMinumum() 
     * @see #getMaximum() 
     */
    @Override
    public boolean accept(File pathname) {
        return pathname != null && pathname.isFile() && 
                (!isMinimumSizeSet() || pathname.length() >= min) && 
                (!isMaximumSizeSet() || pathname.length() <= max);
    }
}
