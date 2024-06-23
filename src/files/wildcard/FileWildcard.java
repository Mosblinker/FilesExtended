/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.wildcard;

import files.FilesExtended;
import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This represents a wildcard for matching files. This uses a PathMatcher with 
 * glob syntax. For more information on glob syntax, see {@code 
 * FileSystem.getPathMatcher()}.
 * @author Milo Steier
 * @see java.nio.file.FileSystem#getPathMatcher(java.lang.String) 
 */
public class FileWildcard implements java.io.FileFilter, PathMatcher{
    /**
     * This is the syntax used by FileWildcard for its PathMatcher. 
     */
    protected static final String DEFAULT_SYNTAX = "glob";
    /**
     * The PathMatcher used to match files.
     */
    private final PathMatcher matcher;
    /**
     * The pattern to use for the PathMatcher.
     */
    private final String card;
    /**
     * This constructs a FileWildcard with the given FileSystem and pattern. <p>
     * FileWildcards use glob syntax.
     * @param system The FileSystem to use.
     * @param wildcard The pattern for the wildcard (Cannot be null).
     * @see FileSystem#getPathMatcher(java.lang.String) 
     */
    public FileWildcard(FileSystem system, String wildcard) {
        card=new File(Objects.requireNonNull(wildcard,"Wildcard pattern cannot be null")).getName();
        matcher = system.getPathMatcher(DEFAULT_SYNTAX+":"+card);
    }
    /**
     * This constructs a FileWildcard with the default FileSystem and with 
     * the given pattern. <p>
     * FileWildcards use glob syntax.
     * @param wildcard The pattern for the wildcard (Cannot be null).
     * @see FileSystem#getPathMatcher(java.lang.String) 
     * @see FileSystems#getDefault() 
     */
    public FileWildcard(String wildcard) {
        this(FileSystems.getDefault(), wildcard);
    }
    /**
     * This gets the pattern of the wildcard.
     * @return The pattern of the wildcard.
     */
    @Override
    public String toString() {
        return card;
    }
    /**
     * This gets the pattern of the wildcard as a <code>File</code>.
     * @return The pattern of the wildcard as a <code>File</code>.
     */
    public File getWildcardFile() {
        return new File(card);
    }
    @Override
    public boolean matches(Path path) {
        return matcher.matches(path.getFileName());
    }
    /**
     * This returns whether the given file has a path that matches this 
     * wildcard.
     * @param file The file to match.
     * @return Whether the file matches this wildcard.
     * @see matches(java.nio.file.Path)
     */
    public boolean matches(File file) {
        return matches(file.toPath());
    }
    @Override
    public boolean accept(File pathname) {
        return matches(pathname);
    }
    /**
     * This generates a <code>javax.swing.filechooser.FileFilter</code> using 
     * this as the filter, and using the given description.
     * @param description The description of the 
     * <code>javax.swing.filechooser.FileFilter</code>.
     * @return A <code>javax.swing.filechooser.FileFilter</code> that filters 
     * out files that do not match this FileWildcard.
     * @see FilesExtended#convertFileFilter(java.io.FileFilter, java.lang.String) 
     * @see #accept(java.io.File) 
     */
    public javax.swing.filechooser.FileFilter getFileChooserFilter(
            String description){
        return FilesExtended.convertFileFilter(this, description);
    }
    /**
     * This generates a <code>javax.swing.filechooser.FileFilter</code> using 
     * this as the filter, and using the wildcard as the description.
     * @return A <code>javax.swing.filechooser.FileFilter</code> that filters 
     * out files that do not match this FileWildcard.
     * @see #getFileChooserFilter(java.lang.String) 
     * @see #accept(java.io.File) 
     */
    public javax.swing.filechooser.FileFilter getFileChooserFilter(){
        return getFileChooserFilter(card);
    }
    /**
     * This compares this FileWildcard with a given Object to see if the Object 
     * matches this FileWildcard.
     * @param obj The Object to compare with.
     * @return Whether the Object is a FileWildcard object with matching data.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)    // If obj is this FileWildcard
            return true;
        if (!(obj instanceof FileWildcard))//If the object is not a FileWildcard
            return false;
        FileWildcard temp = (FileWildcard) obj; // Gets the FileWildcard
        return new File(card).equals(new File(temp.card));
    }
    /**
     * This returns the hash code for this FileWildcard object.
     * @return The hash code for this FileWildcard object.
     */
    @Override
    public int hashCode() {
        int hash = 5;   // The hashcode
        hash = 11 * hash + Objects.hashCode(this.matcher);
        hash = 11 * hash + this.card.hashCode();
        return hash;
    }
//    
//    public FileWildcard getExtensionWildcard(){
//        if (!card.equals("*") && !card.startsWith("*.")){
//            if (card.startsWith("*"))
//                
//        }
//        return this;
//    }
    /**
     * This adds wildcards from a String listing the wildcards to an ArrayList 
     * of FileWildcards.
     * <p>
     * Postcondition: This will alter the given ArrayList.
     * @param cardList The String holding the wildcards.
     * @param wildcards The ArrayList of wildcards to add to.
     * @return The given ArrayList after the wildcards have been added.
     */
    public static ArrayList<FileWildcard>getWildcardsFromString(String cardList, 
            ArrayList<FileWildcard> wildcards) {
        int start = 0;              // The start of the wildcard.
            // A for loop to process the wildcards
        for (int pos = 0; pos < cardList.length(); pos++) {
            boolean split = false;  //Get the end of a wildcard has been reached
            char character = cardList.charAt(pos);  // The current character
            if (character == '\"') {                // If a quotation mark
                start = ++pos;
                pos = cardList.indexOf("\"",pos);
                split = pos >= 0;
            }   // If there is a space, comma, or file path separator
            else if (character == ',' || character == ' ' || 
                    character == File.pathSeparatorChar)
                split = true;
            if (split) {            // If the end of a wildcard has been reached
                    // If the position is greater than the start and the 
                if (pos > start &&pos<cardList.length()){// position is in range
                        // This gets the wildcard as a string
                    String temp = cardList.substring(start,pos).trim();
                    if (!temp.isEmpty())    // If the wildcard is not blank
                        wildcards.add(new FileWildcard(temp));
                }
                start = pos+1;
            }
            else if (pos < 0)   // If the position is negative
                break;
        }
        if (start < cardList.length()) {    // If the start is still in range
                // This gets the wildcard as a string
            String temp = cardList.substring(start).trim();
            if (!temp.isEmpty())    // If the wildcard is not blank
                wildcards.add(new FileWildcard(temp));
        }
        return wildcards;
    }
    /**
     * This gets wildcards from a String listing the wildcards to an ArrayList 
     * of FileWildcards.
     * @param cardList The String holding the wildcards.
     * @return The ArrayList of wildcards.
     */
    public static ArrayList<FileWildcard> getWildcardsFromString(String cardList) {
        return FileWildcard.getWildcardsFromString(cardList,new ArrayList<FileWildcard>());
    }
    /**
     * This removes duplicate wildcards from the given ArrayList of 
     * FileWildcards. <p>
     * Postcondition: This will alter the given ArrayList.
     * @param wildcards The ArrayList of wildcards.
     * @return The ArrayList of wildcards with duplicates removed.
     */
    public static List <FileWildcard> removeDuplicateWildcards(List <FileWildcard> wildcards) {
        // A for loop to find duplicates
        for (int pos = 0; pos < wildcards.size(); pos++) {       
            FileWildcard temp = wildcards.get(pos);  // The element to check for
                // While the last index of the element is not the current one
            while (wildcards.lastIndexOf(temp) != pos){
                wildcards.remove(wildcards.lastIndexOf(temp));
            }
        }
        return wildcards;
    }
    /**
     * This returns a String stating all non-duplicated wildcards, separating 
     * them with the native system's path separator. <p>
     * Postcondition: This will alter the given ArrayList.
     * @param wildcards The ArrayList of wildcards to list.
     * @return A String stating all non-duplicate wildcards.
     */
    public static String getWildcardList(List<FileWildcard> wildcards){
        removeDuplicateWildcards(wildcards);
        if (wildcards.isEmpty())    // If there are no wildcards
            return "";
        String paths = "";          // This gets the string containing the wildcards
        for (FileWildcard temp : wildcards)  // A for loop to go though the wildcards
            paths += "\"" + temp + "\"" + File.pathSeparator + " ";
        return paths.substring(0,paths.length()-(File.pathSeparator.length()+1));
    }
    /**
     * This formats the given ArrayList of wildcards so that they may be used 
     * for file extensions.
     * @param wildcards The wildcards to format.
     * @return An ArrayList of wildcards formatted for file extensions.
     */
    public static ArrayList<FileWildcard> getExtensionWildcards(ArrayList<FileWildcard> wildcards){
            // An arraylist to get the wildcards
        ArrayList<FileWildcard> arr = new ArrayList<FileWildcard>();
        for (FileWildcard temp : wildcards) {   // A for loop to format the wildcards
                // If the wildcard is null
            if (temp == null)
                continue;
            String t = temp.toString();         // Gets the current wildcard.
            if (t.isBlank())                    // If the wildcard is blank
                continue;
            else if (!t.equals("*")){           // If it's not an all accepting wildcard
                if (t.startsWith("."))          // If the wildcard begins with a period
                    t = "*" + t;
                if (!t.startsWith("*."))// If the wildcard does not begin with the proper format
                    t = "*." + t;
            }
            arr.add(new FileWildcard(t));
        }
        return arr;
    }
    /**
     * This returns whether the given path matches any of the wildcards in the 
     * given ArrayList.
     * @param wildcards The ArrayList of wildcards.
     * @param path The path to check for.
     * @return Whether the given path matches any of the wildcards.
     * @see FileWildcard#matches(java.nio.file.Path) 
     */
    public static boolean matches(List<FileWildcard> wildcards, Path path){
        for (FileWildcard temp : wildcards) // A for loop to go through the array
            if (temp.matches(path))         // If a wildcard matches the path
                return true;
        return false;
    }
    /**
     * This returns whether the given file matches any of the wildcards in the 
     * given ArrayList.
     * @param wildcards The ArrayList of wildcards.
     * @param path The file to check for.
     * @return Whether the given file matches any of the wildcards.
     * @see FileWildcard#matches(java.util.ArrayList, java.nio.file.Path) 
     * @see FileWildcard#matches(java.io.File) 
     */
    public static boolean matches(List<FileWildcard> wildcards, File path){
        for (FileWildcard temp : wildcards) // A for loop to go through the array
            if (temp.matches(path))         // If a wildcard matches the file
                return true;
        return false;
    }
}
